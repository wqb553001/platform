package com.activitiserver.config;

import com.activitiserver.core.JdbcLocalUserDetailsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class DetailsServiceConfig {
    private static final Logger log = LoggerFactory.getLogger(DetailsServiceConfig.class);

    @Resource(name = "assumedDataSource")
    DataSource assumedDataSource;

    @Bean
    public UserDetailsService myUserDetailsService(){
        JdbcLocalUserDetailsManager jdbcLocalUserDetailsManager = new JdbcLocalUserDetailsManager(assumedDataSource);
        return jdbcLocalUserDetailsManager;
    }
}

