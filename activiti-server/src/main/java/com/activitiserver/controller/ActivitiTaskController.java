package com.activitiserver.controller;

import com.activitiserver.core.SecurityUtil;
import com.activitiserver.core.ActivitiHandler;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/activiti/task")
@RestController
public class ActivitiTaskController {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiTaskController.class);

    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRuntime taskRuntime;

    private static final String accountbookId = "2c91e3ec6ad89cfb016ae4657a010362"; 		// 110100掌上纵横
    private static final String departDetailId = "2c91e3ec6ad89cfb016ae4657a0c0368";

    @RequestMapping(value="/openTask", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String openTask(){
        try {
            // 27	U00224_王飞	ROLE_ACTIVITI_ADMIN
            // 14	U00116_李岩	GROUP_120100网乐_06-网游
            String empNoAndUsername = "U00224"+ ActivitiHandler.Connector +"王飞";
            String group = "120100网乐_06-网游";
            securityUtil.logInAs(empNoAndUsername);
            logger.info("> create a Group Task for '"+group+"'");
            taskRuntime.create(TaskPayloadBuilder.create()
                    .withName("请假流程")
                    .withDescription("这是一个请假流程 test")
//                    .withCandidateGroup("110100掌上纵横_06-网游")
                    .withCandidateGroup(group)
                    .withPriority(10)
                    .build());

            empNoAndUsername = "U00058"+ ActivitiHandler.Connector +"郝为";
            securityUtil.logInAs(empNoAndUsername);
            Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
            logger.info("User " + empNoAndUsername + " has task number is :" + tasks.getTotalItems());

            empNoAndUsername = "U00116"+ ActivitiHandler.Connector +"李岩";
            securityUtil.logInAs(empNoAndUsername);
            tasks = taskRuntime.tasks(Pageable.of(0, 10));
            logger.info("User " + empNoAndUsername + " has task number is :" + tasks.getTotalItems());

            String availableTaskId = tasks.getContent().get(0).getId();
            logger.info("> Claiming the task for User " + empNoAndUsername);
            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(availableTaskId).build());

            logger.info("> Complete the task for User " + empNoAndUsername);
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(availableTaskId).build());

            empNoAndUsername = "U00302"+ ActivitiHandler.Connector +"杨毅";
            empNoAndUsername = "U00116"+ ActivitiHandler.Connector +"李岩";
            securityUtil.logInAs(empNoAndUsername);
            tasks = taskRuntime.tasks(Pageable.of(0, 10));
            logger.info("User " + empNoAndUsername + " has not complete's task number is :" + tasks.getTotalItems());
        }catch (Exception e){
            e.printStackTrace();
            return "Fail for  "+ Instant.now();
        }
        return "SUCCESS for  "+ Instant.now();
    }

    //    @RequestMapping(value = {"/queryDeployment"}, method = RequestMethod.GET)
    @RequestMapping(value = {"/queryDeployment"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String queryDeployment(){
        return this.queryProcessDefinition(null);
    }

    @RequestMapping(value = {"/queryDeployment/{deploymentIds}"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String queryDeploymentByDeploymentIds(@PathVariable String deploymentIds){
        return this.queryProcessDefinition(deploymentIds);
    }

    private String queryProcessDefinition(String deploymentIds){
        List<ProcessDefinition> processDefinitions;
        String [] dms;
        if(Objects.nonNull(deploymentIds)){
            dms = deploymentIds.split(",");
            if(org.assertj.core.util.Arrays.isNullOrEmpty(dms)){
                return "deploymentIds 不能为空";
            }
            processDefinitions = repositoryService.createProcessDefinitionQuery()
                    .deploymentIds(deploymentParamHandle(dms)).list();
        }else{
            processDefinitions = repositoryService.createProcessDefinitionQuery().list();
        }
        if(CollectionUtils.isEmpty(processDefinitions)){
            return "不存在流程定义 ";
        }
        return resultHandler(processDefinitions).toString();
    }

    private StringBuffer resultHandler(List<ProcessDefinition> processDefinitions){
        StringBuffer stringBuffer = new StringBuffer();
        for (ProcessDefinition processDefinition: processDefinitions) {
            stringBuffer.append("ProcessDefinition{ ");
            stringBuffer.append("Id:"+processDefinition.getId());
            stringBuffer.append(", Key:" + processDefinition.getKey());
            stringBuffer.append(", Name:" + processDefinition.getName());
            stringBuffer.append(", Category:" + processDefinition.getCategory());
            stringBuffer.append(", TenantId:" + processDefinition.getTenantId());
            stringBuffer.append("}").append("\n\r");
            logger.info("ProcessDefinition{ Id:"+processDefinition.getId()
                    + ", Key:" + processDefinition.getKey()
                    + ", Name:" + processDefinition.getName()
                    + ", Category:" + processDefinition.getCategory()
                    + ", TenantId:" + processDefinition.getTenantId()+"}");
        }
        return stringBuffer;
    }

    private Set<String> deploymentParamHandle(String[] deploymentIds){
        return Arrays.stream(deploymentIds).collect(Collectors.toSet());
    }

    @RequestMapping(value = "/processInstance/{idOrkey}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String processInstance(@PathVariable String idOrkey){
        String result;
        ProcessInstance processInstance;
        processInstance = runtimeService.startProcessInstanceByKey(idOrkey);
        if(Objects.isNull(processInstance)){
            processInstance = runtimeService.startProcessInstanceById(idOrkey);
        }

        if(Objects.isNull(processInstance)){
            if(StringUtils.isBlank(idOrkey)){
                result = "未找任何流程定义，无法进行 实例部署";
                logger.info(result);
                return result;
            }
            result = "Not Found id Or Key is:" + idOrkey + "的流程定义，无法进行 实例部署";
            logger.info(result);
            return result;
        }
        result = "ProcessInstance{ Id:"+processInstance.getId()
                + ", BusinessKey:" + processInstance.getBusinessKey()
                + ", DeploymentId:" + processInstance.getDeploymentId()
                + ", ProcessDefinitionId:" + processInstance.getProcessDefinitionId()
                + ", ProcessDefinitionKey:" + processInstance.getProcessDefinitionKey()
                + ", ProcessDefinitionName:" + processInstance.getProcessDefinitionName()
                + ", Description:" + processInstance.getDescription()
                + ", Name:" + processInstance.getName()
                + ", TenantId:" + processInstance.getTenantId()+"}";
        logger.info(result);
        return result;
    }

    public String reployTask(){



        return null;
    }
}
