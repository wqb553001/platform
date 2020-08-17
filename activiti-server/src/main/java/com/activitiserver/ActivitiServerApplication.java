package com.activitiserver;

import com.activitiserver.server.UserBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
public class ActivitiServerApplication implements CommandLineRunner {

	private UserBridgeService userBridgeService;

	@Autowired // 方法 即可用是 public 也可以是 private
	private void setUserBridgeService(UserBridgeService userBridgeService){
		this.userBridgeService = userBridgeService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ActivitiServerApplication.class, args);
	}

	@Override
	/**
	 * CommandLineRunner 用于初始化
	 * 		如果有多个类实现CommandLineRunner接口，
	 * 		实现类上使用@Order注解（或者实现Order接口）来表明顺序
	 */
	public void run(String... args) throws Exception {
		userBridgeService.initRootUsedToLogin();
	}
}


