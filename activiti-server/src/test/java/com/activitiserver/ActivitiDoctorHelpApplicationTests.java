package com.activitiserver;

import com.activitiserver.feignclient.FeignUserService;
import com.activitiserver.server.UserBridgeService;
import com.alibaba.fastjson.JSONObject;
import com.doctor.assistant.commonserver.common.result.PlatformResult;
import com.doctor.assistant.commonserver.utils.JsonUtil;
import com.doctor.assistant.userserver.springdata.entity.TSUser;
import lombok.SneakyThrows;
import org.apache.commons.math.MathRuntimeException;
import org.apache.commons.math.exception.util.LocalizedFormats;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

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

	@Test
	public void contextLoads() {
	}

	public static void main(String[] args) throws InterruptedException {
//		int time = 2;
//		printTest(time);
//		nodeTest();
//		arrayCopyTest();
//		threadLocalMethod();
		ActivitiDoctorHelpApplicationTests activitiDoctorHelpApplicationTests = new ActivitiDoctorHelpApplicationTests();
		activitiDoctorHelpApplicationTests.testMutil();
	}

	private void testMutil(){
		Thread t1 = new Thread(new T1());
		Thread t2 = new Thread(new T2());
		Thread t3 = new Thread(new T3());
		t1.start();
		t2.start();
		t3.start();
	}

	class T1 implements Runnable {
		public void run() {
			try {
				for(int i=0;i<10;i++){
//					System.out.println(i);
					System.out.println(i + "线程：" + Thread.currentThread().getName());
					Thread.sleep(100);  //模拟耗时任务
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class T2 implements Runnable {
		public void run() {
			try {
				for(int i=0;i>-10;i--){
//					System.out.println(i);
					System.out.println(i + "线程：" + Thread.currentThread().getName());
					Thread.sleep(100);  //模拟耗时任务
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class T3 implements Runnable {
		public void run() {
			System.out.println("线程名：" + Thread.currentThread().getName() + "，begin ……");
			Thread t4_1 = new Thread(new T4());
			Thread t5_1 = new Thread(new T5());
			t4_1.start();
			t5_1.start();
			System.out.println("线程名：" + Thread.currentThread().getName() + "，end ...");
		}
	}

	class T4 implements Runnable {
		public void run() {
			try {
				for(int i=1000;i>900;i-=10){
					System.out.println(i + "线程：" + Thread.currentThread().getName());
					Thread.sleep(100);  //模拟耗时任务
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	class T5 implements Runnable {
		public void run() {
			try {
				for(int i=99;i>0;i-=11){
					System.out.println(i + "线程：" + Thread.currentThread().getName());
					Thread.sleep(100);  //模拟耗时任务
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static InnerThreadClass threadInstance = new InnerThreadClass();
	private static void threadLocalMethod(){
		threadInstance.start();
		threadInstance.start();
	}

	static class InnerThreadClass extends Thread{
//		InnerThreadClass threadInstance = null;
//		private InnerThreadClass(){}
//		public static InnerThreadClass getThreadInstance(){
//			return threadInstance = new InnerThreadClass();
//		}

		@SneakyThrows
		@Override
		public void run() {
			System.out.println("线程："+ InnerThreadClass.class + "开始执行 run()……");
			Thread.sleep(3000);
			System.out.println("线程："+ InnerThreadClass.class + "执行结束 run()。。。");
		}
	}

	private static void arrayCopyTest(){
		int[] begin = {1,2,3,4,5};
		int[] end = new int[3];
		System.arraycopy(begin,0,end,0, 5);
		for (int a: end) {
			System.out.print(a);
		}
	}

	private static void nodeTest(){
		Node node4 = new Node(4);
		node4.next = null;
		Node node3 = new Node(3);
		node3.next = node4;
		Node node2 = new Node(2);
		node2.next = node3;
		Node node1 = new Node(1);
		node1.next = node2;
//		System.out.println(node1.toString());
//		System.out.println(reverse(node1).toString());
//		System.out.println("result3: " + reverseList(node1));
		System.out.println("result4: " + test2(node1));
	}
	public static Node reverseList(Node node) {
		Node pre = null;
		Node next = null;
		while (node != null) {
			next = node.next;
			node.next = pre;
			pre = node;
			node = next;
		}
		return pre;
	}

	private static Node test2(Node node){
		Node pre = null;
		Node next = null;
		while (node != null){
//			next = node.next;
//			node.next = pre;
//			pre = node;
//			node = next;

//			next = node.next = pre = node = next;
//			temp = node.next;
//			temp.next = result;
//			result = temp;
//			node = temp;
		}
		return  pre;
	}

	class ListNode {
		int value;
		ListNode next;

		public ListNode(int data) {
			this.value = data;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}

	public static class Node {
		public int value;
		public Node next = null;

		public Node(int data) {
			this.value = data;
		}

		@Override
		public String toString() {
			String result = null;
			Node pre = this;
			while (pre.next != null) {
				result += ("-->" + pre.value);
				pre = pre.next;
			}
			result += ("-->" + pre.value);
			return result;

		}
	}

	public static Node reverse(Node head) {
		if (head == null || head.next == null)
			return head;
		Node temp = head.next;
		Node newHead = reverse(head.next);
		temp.next = head;
		head.next = null;
		return newHead;
	}

	private static void printTest(int n) {
		int line = n - 1;
		printHead(line);        // 先打印上部分
		printBound(line);        // 打印下部分
	}

	// 打印上部分
	private static void printHead(int head) {
		for (int i = head; i > 0; i--) {
			printStr(2 * i + 1);
		}
	}

	// 打印下部分
	private static void printBound(int bound) {
		for (int i = 0; i <= bound; i++) {
			printStr(2 * i + 1);
		}
	}

	// 打印多少个 * 字符
	private static void printStr(int num) {
		StringBuilder strBu = new StringBuilder();
		for (int i = 0; i < num; i++) {
			strBu.append("*");
		}
		System.out.println(strBu.toString());
	}

	private static void call() {
//		int total = 13;
//		int numberTypeBit = 7;
//		int len = total % numberTypeBit == 0 ? total / numberTypeBit : (total / numberTypeBit + 1);
//		System.out.println(len);
//		byteFlagInt();

//		byte[] byteCollection = {1,3,5,7,8,12,13, 25, 127};
//		int maxNum = 14;// 14个成员，最大编号 14
//		byteFlagByte(byteCollection, maxNum);
	}


	@Test
	public static void byteFlagInt() {
		int[] intCollection = {1, 2, 5, 7, 8, 12, 13, 108, 189, 268, 569, 1987, 2020};
		int maxNum = 2020;            // 最大编号 2020
		int numberTypeBit = 31;       // 1 int = 32 bit,减去1位符号位为31
		int len = MathUtils.getLen(maxNum, numberTypeBit);
		int[] groups = new int[len];
		for (int i = 0; i < intCollection.length; i++) {
			int e = intCollection[i];
			int size = MathUtils.getLen(e, numberTypeBit);
			int index = e % numberTypeBit;
			int group = groups[size - 1];
			int merge = group | org.apache.commons.math.util.MathUtils.pow(2, index);
			groups[size - 1] = merge;
		}
		System.out.println("需要标记数长度：" + len + "，即 需要：" + len + "个int数对" + Arrays.toString(intCollection) + "进行标记");
		int[] eles = {9, 12, 1987};
		for (int i = 0; i < eles.length; i++) {
			int ele = eles[i];
			int index_of_group = MathUtils.getLen(ele, numberTypeBit);
			int inner_group_step = ele % numberTypeBit;
			int group = groups[index_of_group - 1];
			int check_result = group & org.apache.commons.math.util.MathUtils.pow(2, inner_group_step);
			if (check_result > 0) {
				System.out.println("元素:" + ele + ",在标记集合中!");
			} else {
				System.out.println("元素:" + ele + ",不在标记集合中!");
			}
		}

	}

	public static void byteFlagByte(byte[] byteCollection, int maxNum) {
		byte numberTypeBit = 7;
		int len = MathUtils.getLen(maxNum, numberTypeBit);
		byte[] groups = new byte[len];
		for (int i = 0; i < byteCollection.length; i++) {
			byte e = byteCollection[i];
			int size = MathUtils.getLen(e, numberTypeBit);
			byte index = (byte) (e % numberTypeBit);
			byte group = groups[size - 1];
			byte merge = (byte) (group | MathUtils.pow((byte) 2, index));
			groups[size - 1] = merge;
		}

		System.out.println("需要标记数长度：" + len + "，即 需要：" + len + "个byte数对" + Arrays.toString(byteCollection) + "进行标记");
		byte[] eles = {6, 9, 12};
		for (int i = 0; i < eles.length; i++) {
			byte ele = eles[i];
			int index_of_group = MathUtils.getLen(ele, numberTypeBit);
			byte inner_group_step = (byte) (ele % numberTypeBit);
			byte group = groups[index_of_group - 1];
			int check_result = group & MathUtils.pow((byte) 2, inner_group_step);
			if (check_result > 0) {
				System.out.println("元素:" + ele + ",在标记集合中!");
			} else {
				System.out.println("元素:" + ele + ",不在标记集合中!");
			}
		}
	}

	public static class MathUtils {
		public static int getLen(int maxNum, int numberTypeBit) {
			return (maxNum % numberTypeBit == 0) ? (maxNum / numberTypeBit) : (maxNum / numberTypeBit + 1);
		}

		public static byte pow(byte k, byte e) throws IllegalArgumentException {
			if (e < 0) {
				throw MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, new Object[]{k, e});
			} else {
				byte result = 1;

				for (byte k2p = k; e != 0; e >>= 1) {
					if ((e & 1) != 0) {
						result *= k2p;
					}

					k2p *= k2p;
				}

				return result;
			}
		}
	}

	//	public static void main(String[] args){
//		System.out.println("开始");
//		A a = new A();
//		SoftReference<A> sr = new SoftReference<A>(a);
//		a = null;
//		if(sr!=null){
//			a = sr.get();
//		}
//		else{
//			a = new A();
//			sr = new SoftReference<A>(a);
//		}
//		System.out.println("结束");
//	}
	static class A {
		int[] a;

		public A() {
			a = new int[100000000];
		}
	}

	@Test
	public void userAll() {
//		String userAllStr = feignUserService.userByLimit(200);
//		List<TSUser> userList = JsonUtil.jsonToList(userAllStr, TSUser.class);
		Object userAllStr = feignUserService.userByLimit(200);
		PlatformResult platformResult = (PlatformResult) userAllStr;
		List<TSUser> userList = (List<TSUser>) platformResult.getData();
		if (!CollectionUtils.isEmpty(userList)) {
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
				System.out.println("用性别代表投票：默认 sex=0投0；sex=1投1，该用户为：" + sex);
				int data = org.apache.commons.math.util.MathUtils.pow(2, more);
				int result = inner | data;
				System.out.println("初始值：" + inner + ",次方数:" + data + ",|运算:" + result);
				if ("1".equals(sex)) userArray[index] = result;
			}

			// sex 1: U00095
			String useNoStr = "U00095";
			int empNoOne = Integer.valueOf(useNoStr.substring(1));
			boolean yesNoAgreeWith = yesNoAgreeWith(empNoOne, userArray);
			System.out.println("用户 " + useNoStr + " sex 1 投的" + (yesNoAgreeWith ? "【赞成】" : "【反对】"));
			// sex 0: U00089
			useNoStr = "U00089";
			empNoOne = Integer.valueOf(useNoStr.substring(1));
			yesNoAgreeWith = yesNoAgreeWith(empNoOne, userArray);
			System.out.println("用户 " + useNoStr + " sex 0 投的" + (yesNoAgreeWith ? "【赞成】" : "【反对】"));
			int sum = AgreeWithCount(userArray);
			System.out.println("总赞成票数为：" + sum);
			long sex = userList.stream().filter(user -> "1".equals(user.getSex())).count();
			Assert.assertEquals("赞成票数 未对上", sex, sum);
		}
	}

	private boolean yesNoAgreeWith(int empNoOne, int[] userArray) {
		int index = getGroupLen(empNoOne);
		int way = empNoOne % GROUP_STEP;
		int result = org.apache.commons.math.util.MathUtils.pow(2, way);
		int src = userArray[index];
		System.out.println("员工编号：" + empNoOne + ",分组号：" + index + ",对应所得 组内编号：" + way + ",原始值：" + src + ",次方数：" + result + ",运算结果：" + (src & result));
		return (((src & result) > 0) ? true : false);
	}

	private int AgreeWithCount(int[] userArray) {
		int count = Arrays.stream(userArray).map(emp -> {
			return Integer.bitCount(emp);
		}).sum();
		return count;
	}

	private int getGroupLen(int size) {
		int len = size / GROUP_STEP;
		return len;
	}

	@Test
	public void queryAccountbook() {
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
		json.put("condition", "OR");
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
	public void testUserByAccountbookIdAndDepartDetailId() {
		String accountbookId = "2c91e3ec6ad89cfb016ae46578cc0337";    // 120000乐从
		accountbookId = "2c91e3ec6ad89cfb016ae4657a010362";        // 110100掌上纵横
		String departDetailId = "2c91e3ec6ad89cfb016ae4657a0c0368";
		String result = feignUserService.userByDepartDetailIdAndAccountbookId(accountbookId, departDetailId);
		System.out.println(result);
	}

	// 1. 将指定部门的用户信息插入到 工作流用户中间表
	// 2. 检验：查询用户，若不存在，则插入；其他部门插入
	@Test
	public void testInitDataToAssumed() {
		userBridgeService.initAssumed();
	}

	@Test
	public void testClearUserToAssumed() {
		userBridgeService.clearUsers(true);
	}

}
