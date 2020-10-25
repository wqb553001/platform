package com.activitiserver.controller;

import com.activitiserver.server.UserBridgeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doctor.assistant.commonserver.annotation.ResponseResult;
import com.doctor.assistant.commonserver.utils.Results;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@ResponseResult
@RequestMapping("/activiti")
@RestController
public class ActivitiController {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    UserBridgeService userBridgeService;

    private static final String accountbookId = "2c91e3ec6ad89cfb016ae4657a010362"; 		// 110100掌上纵横
    private static final String departDetailId = "2c91e3ec6ad89cfb016ae4657a0c0368";

    @RequestMapping(value="/deployment", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public String deployment(){
        Deployment deployment = repositoryService.createDeployment()//创建一个部署对象
                .name("单据审批-普通")
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

    //    @RequestMapping(value = {"/queryDeployments"}, method = RequestMethod.GET)
    @RequestMapping(value = {"/queryDeployments"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.FOUND)
    public String queryDeployments(){
        return this.handleProcessDefinition(null);
    }

    @RequestMapping(value = {"/queryDeployments/{deploymentIds}"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.FOUND)
    public String queryDeploymentByIds(@PathVariable String deploymentIds){
        return this.handleProcessDefinition(deploymentIds);
    }

    private String handleProcessDefinition(String deploymentIds){
        return resultHandler(queryProcessDefinition(deploymentIds)).toString();
    }

    private StringBuffer resultHandler(Results<List<ProcessDefinition>> resultProcessDefinitions){
        StringBuffer stringBuffer = new StringBuffer();
        if(resultProcessDefinitions == null) return stringBuffer;
        if(resultProcessDefinitions.getState() != 200) return stringBuffer.append(resultProcessDefinitions.getMessage());
        List<ProcessDefinition> processDefinitions = resultProcessDefinitions.getResult();
        if (CollectionUtils.isEmpty(processDefinitions)) return stringBuffer.append(resultProcessDefinitions.getMessage());
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

    private Set<String> stringArrayToSet(String[] deploymentIds){
        return java.util.Arrays.stream(deploymentIds).collect(Collectors.toSet());
    }

    @RequestMapping(value="/destroyDeployments", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.LOOP_DETECTED)
    public String destroyDeployments() {
        return destroyDeploymentsByIds(null);
    }

    @RequestMapping(value={"/destroyDeployments/{deploymentIds}"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String destroyDeploymentsByIds(@PathVariable String deploymentIds){
        Map resultMap = new HashMap();
        JSON json = null;
        String msg = "";
        boolean flag = false;
        Results<List<ProcessDefinition>> resultProcessDefinitions = this.queryProcessDefinition(deploymentIds);
        if(resultProcessDefinitions == null){
            msg = "没能获取到能删除的 流程定义";
            logger.info(msg);
            return msg;
        }
        if(!resultProcessDefinitions.isOK()){
            return resultProcessDefinitions.getMessage();
        }
        if(resultProcessDefinitions.getResult() == null){
            msg = "没能获取到能删除的 流程定义";
            logger.info(msg);
            return msg;
        }
        StringBuilder deploymentIdSb = new StringBuilder();
        String deleteErrs = "";
        List<ProcessDefinition> processDefinitions = resultProcessDefinitions.getResult();
        for (ProcessDefinition pD : processDefinitions ) {
            String deploymentId = pD.getDeploymentId();
            try {
                deleteDeploymentById(deploymentId, true);
                deploymentIdSb.append(deploymentId+"、");
            } catch (Exception e) {
                flag = flag | true;
                String message = "";
                if(pD != null && StringUtils.isBlank(deploymentId)){
                    message="ProcessDefinition.Id: "+pD.getId()+"，ProcessDefinition.Name"+pD.getName()+"，未取得流程定义id，即deploymentId is null";
                }else{
                    deleteErrs = deleteErrs + deploymentId + "、";
                    message="流程定义删除失败，deploymentId: " + pD.getDeploymentId();
                }
                logger.info(message);
                e.printStackTrace();
            }
        }
        String result = deploymentIdSb.toString();
        if(StringUtils.isNotBlank(result)){
            result = result.substring(0, result.length() - 1);
            resultMap.put("DELETE SUCCESS", result);
            msg = "成功删除：" + result;
        }
        if(flag && StringUtils.isNotBlank(deleteErrs)){
            // 存在删除失败的流程定义
            deleteErrs = deleteErrs.substring(0, deleteErrs.length() - 1);
            resultMap.put("DELETE ERROR", deleteErrs);
            msg += ("删除失败："+deleteErrs);
        }
        logger.info("删除流程定义有deploymentId："+result);
        json = new JSONObject(resultMap);

        return json.toJSONString();
    }

    private void deleteDeploymentById(String deploymentId, boolean cascade) throws Exception{
        repositoryService.deleteDeployment(deploymentId, cascade);
    }

    private Results<List<ProcessDefinition>> queryProcessDefinition(String deploymentIds){
        Results<List<ProcessDefinition>> result = Results.getInstall();
        String message = null;
        List<ProcessDefinition> processDefinitions = Collections.emptyList();
        String [] dms;
        if(Objects.nonNull(deploymentIds)){
            dms = deploymentIds.split(",");
            if(org.assertj.core.util.Arrays.isNullOrEmpty(dms)){
                message = "deploymentIds 不能为空";
                result.setOK(false);
                result.setMessage(message);
                logger.info(message);
                return result;
            }
            processDefinitions = repositoryService.createProcessDefinitionQuery()
                    .deploymentIds(stringArrayToSet(dms)).list();
        }else{
            processDefinitions = repositoryService.createProcessDefinitionQuery().list();
        }
        result.setResult(processDefinitions);
        if(CollectionUtils.isEmpty(processDefinitions)){
            message = "不存在流程定义 ";
            result.setOK(false);
            result.setMessage(message);
            logger.info(message);
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/processInstance/processDefinitionKey/{processDefinitionKey}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String processInstanceByProcessDefinitionKey(@PathVariable String processDefinitionKey){
        String result = null;
        String content = pickRandomString();
        org.activiti.api.process.model.ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey(processDefinitionKey)
                .withName("Processing Content: " + content)
                .withVariable("content", content)
                .build());
        logger.info(">>> Created Process Instance: " + processInstance);
        result = "ProcessInstance{ Id:"+processInstance.getId()
                + ", BusinessKey:" + processInstance.getBusinessKey()
                + ", ProcessDefinitionId:" + processInstance.getProcessDefinitionId()
                + ", ProcessDefinitionKey:" + processInstance.getProcessDefinitionKey()
                + ", Name:" + processInstance.getName();
        logger.info(result);
        return result;
    }
    private String pickRandomString() {
        String[] texts = {"hello from london", "Hi there from activiti!", "all good news over here.", "I've tweeted about activiti today.",
                "other boring projects.", "activiti cloud - Cloud Native Java BPM"};
        return texts[new Random().nextInt(texts.length)];
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
