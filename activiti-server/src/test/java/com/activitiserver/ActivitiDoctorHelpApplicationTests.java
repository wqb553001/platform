package com.activitiserver;

import com.activitiserver.feignclient.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiDoctorHelpApplicationTests {

//    @Autowired
//    RepositoryService repositoryService;

	@Autowired
	UserService userService;
	@Test
	public void contextLoads() {
		String empNo = "U00148"; // U00020
		String roleName = "预算专员"; // U00020
		String result = null;
		result = userService.findByEmpNo(empNo);
//		result = userService.findByRoleName(roleName);
		System.out.println("请求结束，结果为：" + result);
//        Deployment deployment = repositoryService.createDeployment()//创建一个部署对象
//                .name("请假流程")
//                .addClasspathResource("diagrams/bill-approve-ge.bpmn")
//                .addClasspathResource("diagrams/bill-approve-ge.png")
//                .deploy();
//        System.out.println("部署ID："+deployment.getId());
//        System.out.println("部署名称："+deployment.getName());
	}

}
