package com.activitiserver;

import com.activitiserver.feignclient.UserActivitiFromService;
import com.activitiserver.server.UserBridgeService;
import com.alibaba.fastjson.JSONObject;
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
	UserActivitiFromService userActivitiFromService;
	@Autowired
	UserBridgeService userBridgeService;
//	@Test
	public void contextLoads() {
	}

	public void queryAccountbook(){
		String empNo = "U00148"; // U00020
		String roleName = "预算专员"; // U00020
//		result = userService.findByEmpNo(empNo);
//		result = userService.findByRoleName(roleName);
		String accountbookCode = null;
		String accountbookName = null;
		accountbookCode = "AC003";
		accountbookName = "110100掌上纵横";
		String condition = "";
		JSONObject json = new JSONObject();
		json.put("condition","OR");
		json.put("accountbookCode", accountbookCode);
		json.put("accountbookName", accountbookName);
		condition = json.toJSONString();
		String result = userActivitiFromService.accountbookByCodeOrName(condition);


		System.out.println("请求结束，结果为：" + result);
//        Deployment deployment = repositoryService.createDeployment()//创建一个部署对象
//                .name("请假流程")
//                .addClasspathResource("diagrams/bill-approve-ge.bpmn")
//                .addClasspathResource("diagrams/bill-approve-ge.png")
//                .deploy();
//        System.out.println("部署ID："+deployment.getId());
//        System.out.println("部署名称："+deployment.getName());
	}

	@Test
	public void testUserByAccountbookIdAndDepartDetailId(){
		String accountbookId = "2c91e3ec6ad89cfb016ae46578cc0337"; 	// 120000乐从
		accountbookId = "2c91e3ec6ad89cfb016ae4657a010362"; 		// 110100掌上纵横
		String departDetailId = "2c91e3ec6ad89cfb016ae4657a0c0368";
		String result = userActivitiFromService.userByDepartDetailIdAndAccountbookId(accountbookId, departDetailId);
		System.out.println(result);
	}

	// 1. 将指定部门的用户信息插入到 工作流用户中间表
	// 2. 检验：查询用户，若不存在，则插入；其他部门插入
	@Test
	public void testInitDataToAssumed(){
		userBridgeService.initAssumed();
	}

	@Test
	public void testClearUserToAssumed(){
		userBridgeService.clearUsers(true);
	}

}
