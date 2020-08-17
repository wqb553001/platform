package com.doctor.assistant.userserver.springdata.entity;

import com.doctor.assistant.userserver.springdata.utils.JsonDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统用户表
 *  @author  张代浩
 */
@Entity
//@Cache(region = "common", usage = CacheConcurrencyStrategy.READ_WRITE) // @Cacheable
@Table(name = "t_s_user")
@PrimaryKeyJoinColumn(name = "id")
public class TSUser extends TSBaseUser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 签名文件 */
	private String signatureFile;
	/** 手机 */
	// @Excel(name = "手机" ,width = 20)
	private String mobilePhone;
	/** 办公电话 */
	// @Excel(name = "办公电话",width = 20)
	private String officePhone;
	/** 邮箱 */
	// @Excel(name = "邮箱",width = 25)
	private String email;
	/** 头像 */
	private String portrait;
	/** 开发权限标志 */
	private String devFlag;
	/** 用户类型  1:系统用户 2:接口用户 */
	private String userType;
	/** 人员类型 */
	private String personType;
	/** 性别 */
	// @Excel(name = "性别",width = 25,dicCode = "sex")
	private String sex;
	/** 工号 */
	private String empNo;
	/** 身份证号 */
	// @Excel(name = "身份证号",width = 25)
	private String citizenNo;
	/** 传真 */
	private String fax;
	/** 联系地址 */
	// @Excel(name = "联系地址",width = 25)
	private String address;
	/** 邮编 */
	private String post;
	/** 备注 */
	// @Excel(name = "备注",width = 25)
	private String memo;
	/** 创建时间 */
	private Date createDate;
	/** 创建人ID */
	private String createBy;
	/** 创建人名称 */
	private String createName;
	/** 修改时间 */
	private Date updateDate;
	/** 修改人 */
	private String updateBy;
	/** 修改人名称 */
	private String updateName;
	/** 入职时间 */
	// @Excel(name="入职时间",format = "yyyy-MM-dd")
//	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date enterDate;
	/** 离职时间 */
	// @Excel(name="离职时间",format = "yyyy-MM-dd")
//	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date leaveDate;
	/** 收款账户 */
	// @Excel(name = "收款账户",width = 25)
	private String bankAccountName;
	/** 收款账号 */
	// @Excel(name = "收款账号",width = 25)
	private String bankAccountNumber;

	private Set<TSRole> roleSet = new HashSet<>();


	@Column(name = "dev_flag", length = 2)
	public String getDevFlag() {
		return devFlag;
	}

	public void setDevFlag(String devFlag) {
		this.devFlag = devFlag;
	}

	@Column(name = "signature_file", length = 100)
	public String getSignatureFile() {
		return this.signatureFile;
	}

	public void setSignatureFile(String signatureFile) {
		this.signatureFile = signatureFile;
	}

	@Column(name = "mobile_phone", length = 30)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "office_phone", length = 20)
	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "create_date", nullable = true)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "create_by", nullable = true, length = 32)
	public java.lang.String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "create_name", nullable = true, length = 32)
	public java.lang.String getCreateName() {
		return this.createName;
	}

	public void setCreateName(java.lang.String createName) {
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
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "update_name", nullable = true, length = 32)
	public java.lang.String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(java.lang.String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "portrait", length = 100)
	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	@Column(name = "user_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name = "person_type")
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "emp_no", unique = true, nullable = false)
	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	@Column(name = "citizen_no")
	public String getCitizenNo() {
		return citizenNo;
	}

	public void setCitizenNo(String citizenNo) {
		this.citizenNo = citizenNo;
	}

	@Column(name = "fax")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "post")
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Nullable
	@Column(name = "enter_date")
//	@JsonDeserialize(using = JsonDateDeserializer.class)
	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	@Nullable
	@Column(name = "leave_date")
//	@JsonDeserialize(using = JsonDateDeserializer.class)
	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	@Column(name = "bank_account_name")
	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	@Column(name = "bank_account_number")
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	@ManyToMany(mappedBy = "userSet")
	public Set<TSRole> getRoleSet(){
		return roleSet;
	}

	public void setRoleSet(Set<TSRole> roleSet){
		this.roleSet = roleSet;
	}
}