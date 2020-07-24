package com.doctor.assistant.userserver.springdata.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色表
 *
 * @author 张代浩
 */
@Entity
@Table(name = "t_s_role")
public class TSRole extends IdEntity implements java.io.Serializable {
	// @Excel(name = "角色名称", width = 20)
	private String roleName;//角色名称
	// @Excel(name = "角色编码", width = 20)
	private String roleCode;//角色编码
	// @Excel(name = "描述", width = 50)
	private String description;//描述
	//// @Excel(name="部门权限组ID")
	private String departAgId;//组织机构ID

	private String roleType;  //角色类型:1部门角色/0系统角色

	private Date createDate;

	private String createBy;

	private String createName;

	private Date updateDate;

	private String updateBy;

	private String updateName;

	private Set<TSUser> userSet = new HashSet<>();


	@Column(name = "rolename", nullable = false, length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "rolecode", length = 10)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name = "create_date", nullable = true)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "create_by", nullable = true, length = 32)
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "create_name", nullable = true, length = 32)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "update_date", nullable = true)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "update_by", nullable = true, length = 32)
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "update_name", nullable = true, length = 32)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "depart_ag_id", nullable = true, length = 32)
	public String getDepartAgId() {
		return departAgId;
	}

	public void setDepartAgId(String departAgId) {
		this.departAgId = departAgId;
	}

	@Column(name = "role_type", nullable = true, length = 32)
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Column(name = "description", nullable = true, length = 100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@JoinTable(name = "t_s_role_user",
			joinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "userid", referencedColumnName = "id")
	)
	@ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//	@ManyToMany(mappedBy = "roleSet")
	public Set<TSUser> getUserSet(){
		return userSet;
	}

	public void setUserSet(Set<TSUser> userSet){
		this.userSet = this.userSet;
	}
}