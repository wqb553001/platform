package com.doctor.assistant.userserver;

import com.doctor.assistant.commonserver.utils.JsonUtil;
import com.doctor.assistant.userserver.springdata.entity.AccountbookEntity;
import com.doctor.assistant.userserver.springdata.entity.DepartDetailEntity;
import com.doctor.assistant.userserver.springdata.entity.TSUser;
import com.doctor.assistant.userserver.springdata.entity.UserDepartEntity;
import com.doctor.assistant.userserver.springdata.repository.*;
import com.doctor.assistant.userserver.springdata.service.UserRoleService;
import com.doctor.assistant.userserver.springdata.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServerApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private DepartDetailRepository departDetailRepository;
	@Autowired
	private UserDepartRepository userDepartRepository;
	@Autowired
	private AccountbookRepository accountbookRepository;
	@Test
	public void contextLoads() {
//		accountbook();
//		departDetail();
//		baseUser();
//		roleUser();
//		userDepartDetailByAccountbookIdAndDepartDetail_Id();
//		departDetailByAccountbookId();
	}
	
	private void print(Object obj){
		String objectToJson = JsonUtil.objectToJson(obj);
		System.out.println(objectToJson);
	}

	private void accountbook(){
		List<AccountbookEntity> accountbookEntities = new ArrayList<>();
		String accountbookCode = null;
		String accountbookName = null;
		accountbookCode = "AC009";
		accountbookCode = "AC003";
		accountbookName = "110100掌上纵横";
//		accountbookEntities =
		AccountbookEntity accountbookEntity = accountbookRepository.findFirstByAccountbookCodeOrAccountbookName(accountbookCode, accountbookName);
		this.print(accountbookEntity);
	}

	private void departDetail(){
		List<DepartDetailEntity> departDetailList = new ArrayList<>();
		departDetailList = departDetailRepository.findAll();
		this.print(departDetailList);
	}


	private void departDetailByAccountbookId(){
		List<DepartDetailEntity> departDetailList = new ArrayList<>();
		departDetailList = userService.findDepartDetailByAccountbookIdAndDepartDetailId(null);
		this.print(departDetailList);
	}

	private void userDepartDetailByAccountbookIdAndDepartDetail_Id(){
		List<UserDepartEntity> userDepartEntities = new ArrayList<>();
		String accountbookId = "2c91e3ec6ad89cfb016ae46578cc0337"; // 120000乐从
		accountbookId = "2c91e3ec6ad89cfb016ae4657a010362"; // 掌上纵横
		String departDetailId = "2c91e3ec6ad89cfb016ae4657a0c0368";
		userDepartEntities = userDepartRepository.findUserDepartEntitiesByAccountbookIdAndDepartDetail_Id(accountbookId, departDetailId);
		this.print(userDepartEntities);
	}

//	@Test
	public void userByAccountbookIdAndDepartDetailId(){
		List<TSUser> userList = new ArrayList<>();
		String accountbookId = "2c91e3ec6ad89cfb016ae46578cc0337"; // 120000乐从
		accountbookId = "2c91e3ec6ad89cfb016ae4657a010362"; // 110100掌上纵横
		String departDetailId = "2c91e3ec6ad89cfb016ae4657a0c0368";
		userList = userService.findUserByAccountbookIdAndDepartDetailId(accountbookId, departDetailId);
		this.print(userList);
	}

	private void baseUser(){
		String queryCondition = "王飞";
		queryCondition = "杨亚娟";
		String empNo = "U00009";
		List<TSUser> userList = new ArrayList<>();
//		userList = userRepository.findAll();
		userList = userRepository.findByUserName(queryCondition);
//		TSUser user = userRepository.findByEmpNo(empNo);
		this.print(userList);
//		this.print(user);
	}

	private void roleUser(){
		String roleName = "会计主管";
//		TSRole role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "一般员工";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "人力资源";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "空权限";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "出纳";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "应收会计";
//		role = roleRepository.findFirstByRoleName(roleName);
		roleName = "应付会计";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "预算专员";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "会计主管";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "财务经理";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "结算";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "运营";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "部门助理";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "发票员";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "分析";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "国炬研发";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "销售经理角色2";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "国炬财务部";
//		role = roleRepository.findFirstByRoleName(roleName);
//		roleName = "管理员";
//		role = roleRepository.findFirstByRoleName(roleName);
//		this.print(role);
		List<TSUser> roleUserList = userRoleService.findByRoleName(roleName);
		this.print(roleUserList);
	}

}
