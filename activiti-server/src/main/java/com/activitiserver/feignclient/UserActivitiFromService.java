package com.activitiserver.feignclient;import org.springframework.cloud.openfeign.FeignClient;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;@FeignClient(value = "USER-SERVER", path = "/user")public interface UserService {    /**     * 根据 员工号 查询员工信息     * @param empNo     * @return     */    @RequestMapping(value = "/empNo/{empNo}", method = RequestMethod.GET)    String userByEmpNo(@PathVariable String empNo);    /**     * 根据 角色名称 查用户信息     * @param roleName     * @return     */    @RequestMapping(value = "/roleName/{roleName}", method = RequestMethod.GET)    String userByRoleName(@PathVariable String roleName);    /**     * 查所有公司信息     * @return     */    @RequestMapping(value = "/accountbookAll", method = RequestMethod.GET)    String accountbookAll();    /**     * 更加条件'AND' OR 'OR'查 公司信息     *     *  condition is JSONObject </>;     *  one key is condition,value is 'and' and 'or', 'or' is default;     *  e.g.     *      String condition = "";     *      String accountbookCode = "XM001";     *      String accountbookName = "小米科技";     * 		JSONObject json = new JSONObject();     * 		json.put("condition","OR");     * 		json.put("accountbookCode", accountbookCode);     * 		json.put("accountbookName", accountbookName);     * 		condition = json.toJSONString();     * @return     */    @RequestMapping(value = "/accountbook/condition/{condition}", method = RequestMethod.GET)    String accountbookByCodeOrName(@PathVariable String condition);    /**     * 根据 accountbookId 查 所有部门信息     * @return     */    @RequestMapping(value = "/departDetail/{accountbookId}", method = RequestMethod.GET)    String departDetailByAccountbookId(@PathVariable(required = true)String accountbookId);    /**     * 根据 departDetailId And accountbookId 查 该部门 的 用户-部门信息     * @return     */    @RequestMapping(value = "/userDepart/accountbookId/{accountbookId}/departDetailId/{departDetailId}", method = RequestMethod.GET)    String userDepartByDepartDetailIdAndAccountbookId(@PathVariable(required = true)String accountbookId, @PathVariable(required = true)String departDetailId);    /**     * 根据 departDetailId And accountbookId 查 该部门的 所有用户信息     * @return     */    @RequestMapping(value = "/accountbookId/{accountbookId}/departDetailId/{departDetailId}", method = RequestMethod.GET)    String userByDepartDetailIdAndAccountbookId(@PathVariable(required = true)String accountbookId, @PathVariable(required = true)String departDetailId);    }