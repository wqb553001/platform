package com.doctor.assistant.ImageRecognition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication
@RefreshScope
@RestController
public class ImageRecognitionApplication  extends SpringBootServletInitializer {

//	@Resource(name = "imageDataSource")
//	private DataSource imageDataSource;
	@Resource(name = "dataSource")
	private DataSource dataSource;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ImageRecognitionApplication.class);
	}

	@Bean
	public JdbcTemplate buildJdbcTemplate(){
		return new JdbcTemplate(dataSource);
	}

	public static void main(String[] args) {
		SpringApplication.run(ImageRecognitionApplication.class, args);
	}
}
