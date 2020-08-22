package com.activitiserver;

import com.activitiserver.feignclient.FeignUserService;
import com.activitiserver.server.UserBridgeService;
import com.alibaba.fastjson.JSONObject;
import com.doctor.assistant.commonserver.utils.JsonUtil;
import com.doctor.assistant.userserver.springdata.entity.TSUser;
import org.apache.commons.math.util.MathUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiDoctorHelpApplicationTests {

	/**
	 * 本值，不得超过31，是最大值。这是用于 int 值的位运算和存储，int 数据为 4 Byte，4*8=32bit，最高位留做 符号位，即取 31 = 32 - 1
	 */
	private static final int GROUP_STEP = 31;
	@Autowired
	FeignUserService feignUserService;
	@Autowired
	UserBridgeService userBridgeService;
//	@Test
	public void contextLoads() {
	}

	@Test
	public void userAll(){
		String userAllStr = feignUserService.userByLimit(200);
		List<TSUser> userList = JsonUtil.jsonToList(userAllStr, TSUser.class);
		if(!CollectionUtils.isEmpty(userList)){
			// 这里的数组长度，跟数据编号的最大编号值有关。若不是 按照 从0开始编号，后面会出现数组越界
			int[] userArray = new int[20];
			int empNo = 0;
			String empNoStr = null;
			for (TSUser user : userList) {
				empNoStr = user.getEmpNo();
				empNo = Integer.valueOf(empNoStr.substring(1));
				System.out.println("员工编码：" + empNoStr + ",对应所得 员工编号：" + empNo);
				int index = getGroupLen(empNo);
				int inner = userArray[index];
				int more = empNo % GROUP_STEP;
				System.out.println("分组号：" + index + ",对应所得 组内编号：" + more);
				String sex = user.getSex();
				System.out.println("用性别代表投票：默认 sex=0投0；sex=1投1，该用户为："+sex);
				int data = MathUtils.pow(2, more);
				int result = inner | data;
				System.out.println("初始值："+inner + ",次方数:"+data+ ",|运算:"+result);
				if("1".equals(sex)) userArray[index] = result;
			}

			// sex 1: U00095
			String useNoStr = "U00095";
			int empNoOne = Integer.valueOf(useNoStr.substring(1));
			boolean yesNoAgreeWith = yesNoAgreeWith(empNoOne, userArray);
			System.out.println("用户 "+useNoStr+" sex 1 投的"+(yesNoAgreeWith ? "【赞成】":"【反对】"));
			// sex 0: U00089
			useNoStr = "U00089";
			empNoOne = Integer.valueOf(useNoStr.substring(1));
			yesNoAgreeWith = yesNoAgreeWith(empNoOne, userArray);
			System.out.println("用户 "+useNoStr+" sex 0 投的"+(yesNoAgreeWith ? "【赞成】":"【反对】"));
			int sum = AgreeWithCount(userArray);
			System.out.println("总赞成票数为："  + sum);
			long sex = userList.stream().filter(user -> "1".equals(user.getSex())).count();
			Assert.assertEquals("赞成票数 未对上",sex, sum);
		}
	}

	private boolean yesNoAgreeWith(int empNoOne, int[] userArray){
		int index = getGroupLen(empNoOne);
		int way = empNoOne % GROUP_STEP;
		int result = MathUtils.pow(2, way);
		int src = userArray[index];
		System.out.println("员工编号：" + empNoOne + ",分组号：" + index + ",对应所得 组内编号：" + way + ",原始值：" + src +",次方数："+result+",运算结果："+(src & result));
		return (((src & result) > 0) ? true : false);
	}

	private int AgreeWithCount(int[] userArray){
		int count = Arrays.stream(userArray).map(emp -> {
			return Integer.bitCount(emp);
		}).sum();
		return count;
	}

	private int getGroupLen(int size){
		int len = size / GROUP_STEP;
		return len;
	}

	@Test
	public void queryAccountbook(){
		String result = null;
		String empNo = "U00148"; // U00020
		String roleName = "预算专员"; // U00020
		result = feignUserService.userByEmpNo(empNo);
//		result = userActivitiFromService.userByRoleName(roleName);
		TSUser us = JsonUtil.jsonToPojo(result, TSUser.class);
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
//		result = userActivitiFromService.accountbookByCodeOrName(condition);


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
		String result = feignUserService.userByDepartDetailIdAndAccountbookId(accountbookId, departDetailId);
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
