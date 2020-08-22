package com.doctor.assistant.userserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.doctor.assistant.commonserver.utils.JsonUtil;
import com.doctor.assistant.userserver.springdata.entity.*;
import com.doctor.assistant.userserver.springdata.repository.*;
import com.doctor.assistant.userserver.springdata.service.UserRoleService;
import com.doctor.assistant.userserver.springdata.service.UserService;
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
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DepartDetailRepository departDetailRepository;
    @Autowired
    private UserDepartRepository userDepartRepository;
    @Autowired
    private AccountbookRepository accountbookRepository;

    /**
     * 查询所有员工信息
     * @return
     */
    @RequestMapping(value = "/list/{limitNum}", method = RequestMethod.GET)
    public String userByLimit(@PathVariable(required = false) int limitNum){
        String result = null;
        List<TSUser> users = userRepository.findByLimit(limitNum);
        result = JsonUtil.objectToJson(users);
        return result;
    }
    /**
     * 根据 员工号 查询员工信息
     * @param empNo
     * @return
     */
    @RequestMapping(value = "/empNo/{empNo}", method = RequestMethod.GET)
    public String userByEmpNo(@PathVariable(required = false) String empNo){
        String result = null;
        if(StringUtils.isNotBlank(empNo)){
            TSUser user = userRepository.findByEmpNo(empNo);
            result = JsonUtil.objectToJson(user);
        }
        return result;
    }

    /**
     * 根据 角色名称 查用户信息
     * @param roleName
     * @return
     */
    @RequestMapping(value = "/roleName/{roleName}", method = RequestMethod.GET)
    public String userByRoleName(@PathVariable(required = false) String roleName){
        String result = null;
        if(StringUtils.isNotBlank(roleName)){
            List<TSUser> userList = userRepository.findByRoleName(roleName);
            result = JsonUtil.objectToJson(userList);
        }
        return result;
    }

    /**
     * 查所有角色信息
     * @return
     */
    @RequestMapping(value = "/roleAll", method = RequestMethod.GET)
    public String roleAll(){
        String result = null;
        List<TSRole> roleList = roleRepository.findAll();
        result = JsonUtil.objectToJson(roleList);
        return result;
    }

    /**
     * 查所有部门信息
     * @return
     */
    @RequestMapping(value = "/departDetailAll", method = RequestMethod.GET)
    public String departDetailAll(){
        String result = null;
        List<DepartDetailEntity> departDetailEntities = departDetailRepository.findAll();
        result = JsonUtil.objectToJson(departDetailEntities);
        return result;
    }

    /**
     * 查所有部门信息
     * @return
     */
    @RequestMapping(value = "/departDetail/{accountbookId}", method = RequestMethod.GET)
    public String departDetailByAccountbookId(@PathVariable(required = true)String accountbookId){
        String result = null;
        List<DepartDetailEntity> departDetailEntities = departDetailRepository.findByAccountbookId(accountbookId);
        result = JsonUtil.objectToJson(departDetailEntities);
        return result;
    }

    /**
     * 查所有公司信息
     * @return
     */
    @RequestMapping(value = "/accountbookAll", method = RequestMethod.GET)
    public String accountbookAll(){
        String result = null;
        List<AccountbookEntity> accountbookEntities = accountbookRepository.findAll();
        result = JsonUtil.objectToJson(accountbookEntities);
        return result;
    }

    /**
     * 更加条件'AND' OR 'OR'查 公司信息
     *
     *  condition is Map;
     *  one key is condition,value is 'and' and 'or', 'or' is default;
     *  e.g.
     *      String condition = "";
     *      String accountbookCode = "XM001";
     *      String accountbookName = "小米科技";
     * 		JSONObject json = new JSONObject();
     * 		json.put("condition","OR");
     * 		json.put("accountbookCode", accountbookCode);
     * 		json.put("accountbookName", accountbookName);
     * 		condition = json.toJSONString();
     * @return
     */
    @RequestMapping(value = "/accountbook/condition/{condition}", method = RequestMethod.GET)
    public String accountbookByCodeOrName(@PathVariable String condition){
        String result = null;
        JSONObject jsonObject = JSONObject.parseObject(condition);
        AccountbookEntity accountbook = new AccountbookEntity();
        String accountbookCode = this.judgeYesNoExistAndGetValue(jsonObject, "accountbookCode");
        String accountbookName = this.judgeYesNoExistAndGetValue(jsonObject, "accountbookName");

        String conditionKey = this.judgeYesNoExistAndGetValue(jsonObject, "condition");
        conditionKey = conditionKey.toUpperCase();
        if(StringUtils.isBlank(conditionKey)){
            accountbook = accountbookRepository.findFirstByAccountbookCodeOrAccountbookName(accountbookCode, accountbookName);
            return JsonUtil.objectToJson(accountbook);
        }

        switch (conditionKey){
            case "AND":
                accountbook = accountbookRepository.findFirstByAccountbookCodeAndAccountbookName(accountbookCode, accountbookName);
                break;
            case "OR":
                accountbook = accountbookRepository.findFirstByAccountbookCodeOrAccountbookName(accountbookCode, accountbookName);
                break;
            default:
                accountbook = accountbookRepository.findFirstByAccountbookCodeOrAccountbookName(accountbookCode, accountbookName);
        }

        result = JsonUtil.objectToJson(accountbook);
        return result;
    }

    private String judgeYesNoExistAndGetValue(JSONObject jsonObject, String key){
        String result = null;
        if(jsonObject.containsKey(key)) result = jsonObject.get(key).toString();
        return result;
    }

    /**
     * 根据 departDetailId And accountbookId 查 该部门 的 用户-部门信息
     * @return
     */
    @RequestMapping(value = "/userDepart/accountbookId/{accountbookId}/departDetailId/{departDetailId}", method = RequestMethod.GET)
    public String userDepartByDepartDetailIdAndAccountbookId(@PathVariable(required = true)String accountbookId, @PathVariable(required = true)String departDetailId){
        String result = null;
        List<UserDepartEntity> departEntities = userDepartRepository.findUserDepartEntitiesByAccountbookIdAndDepartDetail_Id(accountbookId, departDetailId);
        result = JsonUtil.objectToJson(departEntities);
        return result;
    }

    /**
     * 根据 departDetailId And accountbookId 查 该部门的 所有用户信息
     * @return
     */
    @RequestMapping(value = "/accountbookId/{accountbookId}/departDetailId/{departDetailId}", method = RequestMethod.GET)
    public String userByDepartDetailIdAndAccountbookId(@PathVariable(required = true)String accountbookId, @PathVariable(required = true)String departDetailId){
        String result = null;
        List<TSUser> userList = userService.findUserByAccountbookIdAndDepartDetailId(accountbookId, departDetailId);
        result = JsonUtil.objectToJson(userList);
        return result;
    }

}
