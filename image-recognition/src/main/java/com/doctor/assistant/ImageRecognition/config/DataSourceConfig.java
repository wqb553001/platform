package com.doctor.assistant.ImageRecognition.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "imageDataSource")
    @Qualifier("imageDataSource")
    @ConfigurationProperties(prefix="spring.datasource.image")
    public DataSource getMyDataSource(){
        return DataSourceBuilder.create().build();
    }
    
}
