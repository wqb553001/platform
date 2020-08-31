package com.doctor.assistant.scheduleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
public class ScheduleCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleCenterApplication.class, args);
	}

}
