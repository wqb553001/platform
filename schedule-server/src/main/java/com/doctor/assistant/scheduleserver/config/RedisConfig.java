package com.doctor.assistant.scheduleserver.config;import org.springframework.boot.web.client.RestTemplateBuilder;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.web.client.RestTemplate;@Configurationpublic class RedisConfig {    @Bean    public RestTemplate restTemplate(RestTemplateBuilder builder) {        // Do any additional configuration here        return builder.build();    }}