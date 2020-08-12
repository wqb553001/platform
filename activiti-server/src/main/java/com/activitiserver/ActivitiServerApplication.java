package com.activitiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })  // (scanBasePackages = "com.activitiserver.*")
@EnableWebMvc
@EnableDiscoveryClient
@EnableFeignClients
public class ActivitiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiServerApplication.class, args);
	}

}


