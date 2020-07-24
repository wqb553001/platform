package com.doctor.assistant.userserver;

import com.doctor.assistant.userserver.springdata.entity.DepartDetailEntity;
import com.doctor.assistant.userserver.springdata.entity.TSUser;
import com.doctor.assistant.userserver.springdata.repository.*;
import com.doctor.assistant.userserver.springdata.service.UserRoleService;
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
	private UserRepository userRepository;
	@Autowired
	private EAGERUserRepository EAGERrepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private DepartDetailRepository departDetailRepository;
	@Autowired
	private EAGERDepartDetailRepository EAGERdepartDetailRepository;
	@Test
	public void contextLoads() {
//		departDetail();
//		baseUser();
		roleUser();
	}

	private void departDetail(){
		List<DepartDetailEntity> departDetailList = new ArrayList<>();
		departDetailList = departDetailRepository.findAll();
//		departDetailList = EAGERdepartDetailRepository.findAll();
		System.out.println(departDetailList);
	}

	private void baseUser(){
		String queryCondition = "王飞";
		queryCondition = "杨亚娟";
		String empNo = "U00009";
		List<TSUser> userList = new ArrayList<>();
//		userList = userRepository.findAll();
		userList = userRepository.findByUserName(queryCondition);
//		TSUser user = userRepository.findByEmpNo(empNo);
//		userList = EAGERrepository.findAll();
		System.out.println(userList);
//		System.out.println(user);
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
//		System.out.println(role);
		List<TSUser> roleUserList = userRoleService.findByRoleName(roleName);
		System.out.println(roleUserList);
	}

}
