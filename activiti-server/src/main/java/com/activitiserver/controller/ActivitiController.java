package com.activitiserver.controller;

import com.activitiserver.server.UserBridgeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/activiti")
@RestController
public class ActivitiController {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    UserBridgeService userBridgeService;

    private static final String accountbookId = "2c91e3ec6ad89cfb016ae4657a010362"; 		// 110100掌上纵横
    private static final String departDetailId = "2c91e3ec6ad89cfb016ae4657a0c0368";

    @RequestMapping(value="/deployment", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String deployment(){
        Deployment deployment = repositoryService.createDeployment()//创建一个部署对象
                .name("请假流程")
                .addClasspathResource("static/diagrams/bill-approve-ge.bpmn")
                .addClasspathResource("static/diagrams/bill-approve-ge.png")
                .deploy();
        System.out.println("部署ID："+deployment.getId());
        System.out.println("部署名称："+deployment.getName());
        logger.info("Deployment: Id-"+deployment.getId()
                + ", Key-" + deployment.getKey()
                + ", Name-" + deployment.getName()
                + ", Category-" + deployment.getCategory()
                + ", TenantId-" + deployment.getTenantId());
        Map map = new HashMap();
        map.put("deployment-key", "deployment-value");
        map.put("Id", deployment.getId());
        map.put("Key", deployment.getKey());
        map.put("Name", deployment.getName());
        map.put("Category", deployment.getCategory());
        map.put("TenantId", deployment.getTenantId());
        JSON json = new JSONObject(map);

        return json.toJSONString();
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
        return java.util.Arrays.stream(deploymentIds).collect(Collectors.toSet());
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

    @RequestMapping(value = "/initData", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String testInitDataToAssumed(){
        userBridgeService.initAssumed();
        return "initData is Complete!  " + Instant.now();
    }

    @RequestMapping(value = "/dataClear", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String testClearUserToAssumed(@RequestParam(required = false, defaultValue = "false") boolean isTruncate){
        System.out.println("isTruncate : " + isTruncate);
        userBridgeService.clearUsers(isTruncate);
        return "dataClear is Complete!  " + Instant.now();
    }
}
