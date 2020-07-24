package com.doctor.assistant.userserver.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.doctor.assistant.userserver.springdata.entity.TSUser;
import com.doctor.assistant.userserver.springdata.repository.UserRepository;
import com.doctor.assistant.userserver.springdata.service.UserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private DiscoveryClient client;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleService userRoleService;

    /**
     *
     * @param empNo
     * @return
     */
    @RequestMapping(value = "/empNo/{empNo}", method = RequestMethod.GET)
    public String findByEmpNo(@PathVariable(required = false) String empNo){
        String result = null;
        if(StringUtils.isBlank(empNo)){
            TSUser user = userRepository.findByEmpNo(empNo);
            result = JSONUtils.toJSONString(user);
        }
        return result;
    }

    /**
     * 根据 角色名称 查用户信息
     * @param roleName
     * @return
     */
    @RequestMapping(value = "/roleName/{roleName}", method = RequestMethod.GET)
    public String findByRoleName(@PathVariable(required = false) String roleName){
        String result = null;
        if(StringUtils.isBlank(roleName)){
            List<TSUser> userList = userRoleService.findByRoleName(roleName);
            result = JSONUtils.toJSONString(userList);
        }
        return result;
    }
}
