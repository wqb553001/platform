package com.doctor.assistant.ImageRecognition.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
//@EnableTransactionManagement
//@MapperScan(basePackages = "com.doctor.assistant.ImageRecognition.dao")
public class DataSourceConfig {
    /**
     * 数据源-imageDataSource
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource.image")
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }
//    /**
//     * 数据源-imageDataSource
//     * @return
//     */
//    @ConfigurationProperties(prefix = "spring.datasource.image")
//    @Bean(name = "imageDataSource")
//    public DataSource imageDataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "imageSqlSessionFactory")
//    public SqlSessionFactory imageSqlSessionFactory(@Qualifier("imageDataSource") DataSource imageDataSource) throws Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(imageDataSource);
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @Bean(name = "imageDataSourceTransactionManager")
//    public DataSourceTransactionManager imageDataSourceTransactionManager(@Qualifier("imageDataSource") DataSource imageDataSource){
//        return new DataSourceTransactionManager(imageDataSource);
//    }
//
//    @Bean(name = "imageSqlSessionTemplate")
//    public SqlSessionTemplate imageSqlSessionTemplate(@Qualifier("imageSqlSessionFactory") SqlSessionFactory imageSqlSessionFactory){
//        return new SqlSessionTemplate(imageSqlSessionFactory);
//    }
//
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean emf(@Qualifier("imageDataSource") DataSource imageDataSource) {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(imageDataSource);
//        emf.setPackagesToScan(
//                new String[]{"com.doctor.assistant.ImageRecognition.entity"});
//        emf.setJpaVendorAdapter(
//                new HibernateJpaVendorAdapter());
//        return emf;
//    }
//
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory")EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory);
//        return txManager;
//    }

//    @Bean(name = "imageTransactionManager")
//    public PlatformTransactionManager transactionManager(@Qualifier("imageDataSource") DataSource imageDataSource){
//        return new DataSourceTransactionManager(imageDataSource);
//    }

//    @Bean(name = "imageDataSource")
//    @Qualifier("imageDataSource")
//    @ConfigurationProperties(prefix="spring.datasource.image")
//    public DataSource getMyDataSource(DataSourceProperties properties){
//        return DataSourceBuilder.create(properties.getClassLoader())
//                .type(HikariDataSource.class)
//                .driverClassName(properties.determineDriverClassName())
//                .url(properties.determineUrl())
//                .username(properties.determineUsername())
//                .password(properties.determinePassword())
//                .build();
//    }

}
