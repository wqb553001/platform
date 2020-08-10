
# 一. Activiti7体系架构
通过加载activiti.cfg.xml文件得到ProcessEngineConfiguration对象，通过ProcessEngineConfiguration对象可以得到ProcessEngine对象
得到该对象后，可以通过流程引擎对象ProcessEngine来得到各种Service，每一种Service接口有每个用途
* RepositoryService activiti 的资源管理类
* RuntimeService activiti 的流程运行管理类
* TaskService activiti 的任务管理类
* HistoryService activiti 的历史管理类
* ManagerService activiti 的引擎管理类


# 二. Activiti使用：
【前情】—— 步骤：  
(1). 流程定义  
(2). 流程部署  
(3). 创建流程实例  
(4). 查询代办任务  
(5). 任务处理  
(6). 流程结束  
(7). 查看历史流程  

### 1.流程定义
使用Activiti Designer工具创建流程图

新建一个BPMNFile流程图，可更改该流程图的ID和每个任务环节的执行人，流程图就是一个xml文件，每一个流程需要生成一张流程图保存，首先将.bpmn文件改为.xml文件
然后右键该xml文件Diagrams--->show BPMN 2.0 Designer就能生成一张流程图，将流程图导出保存到项目对应目录即可，然后将xml改回bpmn即可



### 2.流程部署
需要将流程部署到Activiti当中，代表当前有该流程    
/**  
\* 流程部署  
\* `act_ge_bytearray` 流程定义的资源信息，包含bpmn和png流程文件信息  
\* `act_re_deployment` 流程部署信息，包含流程名称,ID,Key等  
\* `act_re_procdef` 流程定义信息  
\*/  
@Test  
public void deployment(){  
    //获取ProcessEngine对象 默认配置文件名称：activiti.cfg.xml 并且configuration的Bean实例ID为processEngineConfiguration  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
    //获取RepositoryService对象进行流程部署  
    RepositoryService repositoryService = processEngine.getRepositoryService();  
    //进行部署,将对应的流程定义文件生成到数据库当中，作为记录进行保存  
    Deployment deployment = repositoryService.createDeployment()  
    .addClasspathResource("bpmnfile/holiday.bpmn") //加载流程文件  
    .addClasspathResource("bpmnfile/holiday.png")  
    .name("请假流程") //设置流程名称  
    .key("holidayKey")  
    .deploy(); //部署  
    //输出部署信息  
    System.out.println("流程名称："+deployment.getName());  
    System.out.println("流程ID："+deployment.getId());  
    System.out.println("流程Key："+deployment.getKey());  
}




### 3.创建流程实例
流程定义相当于类
流程实例相当于类的实例(对象)

/**  
\* 启动流程实例  
\* `act_hi_actinst` 已开始和执行完毕的活动信息  
\* `act_hi_identitylink` 历史参与者信息  
\* `act_hi_procinst` 流程实例信息  
\* `act_hi_taskinst` 历史任务实例  
\* `act_ru_execution` 任务执行信息  
\* `act_ru_identitylink` 当前任务参与者  
\* `act_ru_task` 任务信息  
\*/  
@Test  
public void startInstance(){  
    //获取ProcessEngine对象 默认配置文件名称：activiti.cfg.xml 并且configuration的Bean实例ID为processEngineConfiguration  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();   
    //获取到RuntimeService对象  
    RuntimeService runtimeService = processEngine.getRuntimeService();  
    //创建流程实例  
    ProcessInstance holiday = runtimeService.startProcessInstanceByKey("holiday");  
    //输出实例信息  
    System.out.println("流程部署ID："+holiday.getDeploymentId());  
    System.out.println("流程实例ID："+holiday.getId());  
    System.out.println("活动ID："+holiday.getActivityId());  
}


### 4.用户查询代办任务
\/**  
\* 查看代办任务  
\*/  
@Test  
public void getTask(){  
    //获取ProcessEngine对象 默认配置文件名称：activiti.cfg.xml 并且configuration的Bean实例ID为processEngineConfiguration  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
    //获取一个TaskService对象  
    TaskService taskService = processEngine.getTaskService();  
    //查询代办业务 createTaskQuery查询任务 taskCandidateOrAssigned查询任务执行者 processDefinitionKey：查询流程  
    \/**  
    \* taskCandidateOrAssigned匹配规则:1.Assigned 2.配置bpmn文件中定义的值  
    \* taskAssignee匹配规则:1.Assigned  
    \*/  
    \// /* List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned("lisi").processDefinitionKey("holiday").list(); */  
    List<Task> list = taskService.createTaskQuery().taskAssignee("zhangsan").processDefinitionKey("holiday").list();  
    //分页：List<Task> list = taskService.createTaskQuery().taskAssignee("zhangsan").processDefinitionKey("holiday").listPage(i,j);  
    for (Task task:list) {  
        System.out.println("任务名称："+task.getName());  
        System.out.println("任务执行人："+task.getAssignee());  
        System.out.println("任务ID："+task.getId());  
        System.out.println("流程实例ID："+task.getProcessInstanceId());  
    }  
}  


### 5.用户进行任务处理
\/**  
\* 任务处理：当所有任务处理完毕，对应当前流程实例信息删除，但是可以在历史中查看到该信息  
\*/  
@Test  
public void completeTask(){  
    //获取ProcessEngine对象 默认配置文件名称：activiti.cfg.xml 并且configuration的Bean实例ID为processEngineConfiguration  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
    //获取一个TaskService对象  
    TaskService taskService = processEngine.getTaskService();  
    //任务处理  
    taskService.complete("10002");  
}  



### 6.流程结束  

### 7.当业务流程结束后通过历史可以查看到已经走完的流程  
\/**  
\* 查看历史任务  
\*/  
@Test  
public void getHistory() {  
    //获取ProcessEngine对象 默认配置文件名称：activiti.cfg.xml 并且configuration的Bean实例ID为processEngineConfiguration  
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
    //获取HistoryService接口  
    HistoryService historyService = processEngine.getHistoryService();  
    //获取历史任务  
    HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();  
    //获取指定流程实例的任务   
    historicActivityInstanceQuery.processInstanceId("2501");  
    //获取任务列表   
    List<HistoricActivityInstance> list = historicActivityInstanceQuery.list();  
    for (HistoricActivityInstance ai : list) {  
        System.out.println("任务节点ID："+ai.getActivityId());  
        System.out.println("任务节点名称："+ai.getActivityName());  
        System.out.println("流程实例ID信息："+ai.getProcessDefinitionId());  
        System.out.println("流程实例ID信息："+ai.getProcessInstanceId());  
        System.out.println("==============================");  
    }  
}

# Read Me First  
The following was discovered as part of building this project:  

* The original package name 'com.activiti.doctor-help' is invalid and this project uses 'com.activiti.server' instead.  
