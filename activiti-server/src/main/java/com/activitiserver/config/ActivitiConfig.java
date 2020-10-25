package com.activitiserver.config;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * ActivitiEngin 相关配置
 */
@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {

    @Autowired
    @Qualifier("activitiDataSource")
    private DataSource activitiDataSource;
//    @Autowired
//    private Environment env;

    /**
     * 初始化配置，将创建25张表
     *
     *@return
     */
    @Bean
    public StandaloneProcessEngineConfiguration processEngineConfiguration(@Qualifier("activitiDataSource") DataSource activitiDataSource){
        StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration = new StandaloneProcessEngineConfiguration();
        standaloneProcessEngineConfiguration.setDataSource(activitiDataSource);
        //自动更新表结构，数据库表不存在时会自动创建表
        standaloneProcessEngineConfiguration.setDatabaseSchemaUpdate(StandaloneProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //保存历史数据级别设置为full最高级别，便于历史数据的追溯
        standaloneProcessEngineConfiguration.setHistoryLevel(HistoryLevel.FULL);
        //关闭activiti自动部署（使用流程设计器部署，不使用具体文件访问方式）
        standaloneProcessEngineConfiguration.setDbHistoryUsed(true);
        return standaloneProcessEngineConfiguration;
    }

//    @Bean(name = "activitiTransactionManager")
//    public PlatformTransactionManager transactionManager(@Qualifier("activitiDataSource") DataSource activitiDataSource){
//        return new DataSourceTransactionManager(activitiDataSource);
//    }

//    /**
//     * 初始化配置，将创建25张表
//     *
//     *@return
//     */
//    @Bean
//    public SpringProcessEngineConfiguration springProcessEngineConfiguration(@Qualifier("activitiDataSource") DataSource activitiDataSource) {
//        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
//        configuration.setDataSource(activitiDataSource);
//        configuration.setDatabaseSchemaUpdate(SpringProcessEngineConfiguration.DB_SCHEMA_UPDATE_DROP_CREATE);
//        configuration.setHistoryLevel(HistoryLevel.FULL);
//        configuration.setDbHistoryUsed(true);
//        configuration.setAsyncExecutorActivate(false);
//        return configuration;
//    }

//    /**
//     * 创建引擎
//     *@returnProcessEngine
//     */
//    @Bean(name = "processEngine")
//    public ProcessEngine processEngine(@Qualifier("activitiTransactionManager")PlatformTransactionManager transactionManager) {
//        return springProcessEngineConfiguration(transactionManager).buildProcessEngine();
//    }

//    /**
//     * 工作流流程引擎
//     * @param processEngine
//     * @return
//     */
//    @Bean
//    public ProcessEngineEndpoint processEngineEndpoint(@Qualifier("processEngine")ProcessEngine processEngine){
//        return new ProcessEngineEndpoint(processEngine);
//    }

//    /**
//     * 创建引擎
//     *@returnProcessEngine
//     */
//    @Bean(name = "endpointAutoConfiguration")
//    public EndpointAutoConfiguration endpointAutoConfiguration(@Qualifier("processEngine")ProcessEngine processEngine) {
//        EndpointAutoConfiguration endpointAutoConfiguration = new EndpointAutoConfiguration();
//        endpointAutoConfiguration.processEngineEndpoint(processEngine);
//        return endpointAutoConfiguration;
//    }


    /**
     * RepositoryService 管理流程定义
     * @param processEngine
     * @return
     */
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine){
        return processEngine.getRepositoryService();
    }

    /**
     * RuntimeService 执行管理，包括启动、推进、删除流程实例等操作
     * @param processEngine
     * @return
     */
    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine){
        return processEngine.getRuntimeService();
    }

    /**
     * TaskService 任务管理
     * @param processEngine
     * @return
     */
    @Bean
    public TaskService taskService(ProcessEngine processEngine){
        return processEngine.getTaskService();
    }


    /**
     * HistoryService 历史管理(执行完的数据的管理)
     * @param processEngine
     * @return
     */
    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    /**
     * processDiagramGenerator 流程图管理
     * @return
     */
    @Bean
    public ProcessDiagramGenerator processDiagramGenerator() {
        return new DefaultProcessDiagramGenerator();
    }



//    /**
//     * 数据源
//     * @return
//     */
//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
//    public DataSource dataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));
//        return dataSource;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
