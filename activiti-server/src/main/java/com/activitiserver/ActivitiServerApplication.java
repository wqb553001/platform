package com.activitiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication  // (scanBasePackages = "com.activiti.*")
@EnableWebMvc
@EnableDiscoveryClient
public class ActivitiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiServerApplication.class, args);
	}

}


