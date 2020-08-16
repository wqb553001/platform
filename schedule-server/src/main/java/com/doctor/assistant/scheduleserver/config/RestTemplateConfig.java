package com.doctor.assistant.scheduleserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private RestTemplateBuilder builder;

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(){
        return builder.build();
    }

    @Primary
    @LoadBalanced
    @Bean(name = "restEurekaServerTemplate")
    public RestTemplate restEurekaServerTemplate(){
        return new RestTemplate();
    }
}
