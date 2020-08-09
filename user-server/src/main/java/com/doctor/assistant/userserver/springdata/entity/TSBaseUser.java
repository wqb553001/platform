package com.doctor.assistant.userserver.springdata.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统用户父类表
 * @author  张代浩
 */
@Entity
@Table(name = "t_s_base_user")
@Inheritance(strategy = InheritanceType.JOINED)
//@NamedEntityGraph(name = "base_user.Graph", attributeNodes = {
//		@NamedAttributeNode(value = "userOrgSet"),
//		@NamedAttributeNode(value = "userDepartDetailSet",subgraph = "departDetailGraph"),
//		@NamedAttributeNode(value = "userAccountbookSet", subgraph = "accountbookGraph")
//}
//,subgraphs = {
//		@NamedSubgraph(name = "departDetailGraph",attributeNodes = {
//				@NamedAttributeNode(value = "departDetail",subgraph = "departGraph")
//		}),@NamedSubgraph(name = "departGraph",attributeNodes = {
//				@NamedAttributeNode("depart")
//		}),@NamedSubgraph(name = "accountbookGraph",attributeNodes = {
//				@NamedAttributeNode("accountbook")
//		})
//})
public class TSBaseUser extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 用户名 */
	// @Excel(name = "用户名", width = 15)
	private String userName;

	/** 真实姓名 */
	// @Excel(name = "真实姓名", width = 15)
	private String realName;

	/** 用户使用浏览器类型 */
	private String browser;

	/** 用户验证唯一标示 */
	// @Excel(name = "角色编码(多个角色编码用逗号分隔，非必填)", width = 50)
	private String userKey;

	/** 用户密码 */
	private String password;

	/** 是否同步工作流引擎 */
	private Short activitiSync;

	/** 状态1：在线,2：离线,0：禁用 */
	private Short status;

	/** 状态: 0:不删除  1：删除 */
	private Short deleteFlag;

	/** 签名文件 */
	private byte[] signature;

	/** 英文名 */
	private String userNameEn;

	//	// @Excel(name = "组织机构编码(多个组织机构编码用逗号分隔，非必填)", width = 50)
	private Set<TSUserOrg> userOrgSet = new HashSet<>();

	/** 部门列表（非表字段，已废弃） */
	private String departid;

	/** 当前部门（非表字段，已废弃） */
	private TSDepart currentDepart = new TSDepart();

	/** 账簿列表（非表字段） */
	private Set<UserDepartEntity> userDepartDetailSet = new HashSet<>();
	/** 当前部门（非表字段） */
	private DepartDetailEntity currentDepartDetail = new DepartDetailEntity();

	/** 账簿列表（非表字段） */
	private Set<UserAccountbookEntity> userAccountbookSet = new HashSet<>();
	/** 当前账簿（非表字段） */
	private AccountbookEntity currentAccountbook = new AccountbookEntity();

	@Column(name = "signature", length = 3000)
	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	@Column(name = "browser", length = 20)
	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	@Column(name = "userkey", length = 200)
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getActivitiSync() {
		return activitiSync;
	}

	@Column(name = "activiti_sync")
	public void setActivitiSync(Short activitiSync) {
		this.activitiSync = activitiSync;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "username", nullable = false)
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "realname", length = 50)
	public String getRealName() {
		return this.realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Transient
	public TSDepart getCurrentDepart() {
		return currentDepart;
	}
	public void setCurrentDepart(TSDepart currentDepart) {
		this.currentDepart = currentDepart;
	}

	// FetchMode可选值意义与区别如下：
	//@ Fetch (FetchMode.JOIN) will use the left join query produced only one sql statement
	//@ Fetch (FetchMode.SELECT) will have N +1 clause sql statement
	//@ Fetch (FetchMode.SUBSELECT) produce two sql statement to use the second statement id in (.....) check out all the data associated
	//
	//@Fetch(FetchMode.JOIN)： 始终立刻加载，使用外连(outer join)查询的同时加载关联对象，忽略FetchType.LAZY设定。
	//@Fetch(FetchMode.SELECT) ：默认懒加载(除非设定关联属性lazy=false)，当访问每一个关联对象时加载该对象，会累计产生N+1条sql语句
	//@Fetch(FetchMode.SUBSELECT)  默认懒加载(除非设定关联属性lazy=false),在访问第一个关联对象时加载所有的关联对象。会累计产生两条sql语句。且FetchType设定有效。
	//
	// FetchType可选值意义与区别如下：
	//FetchType.LAZY: 懒加载，在访问关联对象的时候加载(即从数据库读入内存)
	//FetchType.EAGER:立刻加载，在查询主对象的时候同时加载关联对象。
//	@JsonIgnore
	@Fetch(FetchMode.JOIN)
	@OneToMany(cascade=CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "userId")
	public Set<UserDepartEntity> getUserDepartDetailSet() {
		return userDepartDetailSet;
	}
	public void setUserDepartDetailSet(Set<UserDepartEntity> userDepartDetailSet) {
		this.userDepartDetailSet = userDepartDetailSet;
	}

	@Transient
	public DepartDetailEntity getCurrentDepartDetail() {
		return currentDepartDetail;
	}
	public void setCurrentDepartDetail(DepartDetailEntity currentDepartDetail) {
		this.currentDepartDetail = currentDepartDetail;
	}

	//	@JsonIgnore
	@Fetch(FetchMode.JOIN)
	@OneToMany(cascade=CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "tsUser")
	public Set<TSUserOrg> getUserOrgSet() {
		return userOrgSet;
	}
	public void setUserOrgSet(Set<TSUserOrg> userOrgSet) {
		this.userOrgSet = userOrgSet;
	}

	//	@JsonIgnore
	@Fetch(FetchMode.JOIN)
	@OneToMany(cascade=CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "userId")
	public Set<UserAccountbookEntity> getUserAccountbookSet() {
		return userAccountbookSet;
	}
	public void setUserAccountbookSet(Set<UserAccountbookEntity> userAccountbookSet) {
		this.userAccountbookSet = userAccountbookSet;
	}

	@Transient
	public AccountbookEntity getCurrentAccountbook() {
		return currentAccountbook;
	}
	public void setCurrentAccountbook(AccountbookEntity currentAccountbook) {
		this.currentAccountbook = currentAccountbook;
	}

	@Column(name = "delete_flag")
	public Short getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "user_name_en")
	public String getUserNameEn() {
		return userNameEn;
	}
	public void setUserNameEn(String userNameEn) {
		this.userNameEn = userNameEn;
	}

	@Column(name = "departid", length = 32)
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
	}

	@Override
	public String toString() {
		return "TSBaseUser{" +
				"userName='" + userName + '\'' +
				", realName='" + realName + '\'' +
				", browser='" + browser + '\'' +
				", userKey='" + userKey + '\'' +
				", password='" + password + '\'' +
				", activitiSync=" + activitiSync +
				", status=" + status +
				", deleteFlag=" + deleteFlag +
				", signature=" + Arrays.toString(signature) +
				", userNameEn='" + userNameEn + '\'' +
				", departid='" + departid + '\'' +
				", userOrgSet=" + userOrgSet +
				", currentDepart=" + currentDepart +
				", currentDepartDetail=" + currentDepartDetail +
				", userAccountbookSet=" + userAccountbookSet +
				", currentAccountbook=" + currentAccountbook +
				'}';
	}
}