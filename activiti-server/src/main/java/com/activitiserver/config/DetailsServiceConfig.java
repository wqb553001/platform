package com.activitiserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class DetailsServiceConfig {
    private static final Logger log = LoggerFactory.getLogger(DetailsServiceConfig.class);

    @Bean
    public UserDetailsService myUserDetailsService(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        


        return null;
    }
}

