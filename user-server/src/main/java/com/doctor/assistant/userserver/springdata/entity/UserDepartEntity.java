package com.doctor.assistant.userserver.springdata.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 用户部门映射
 * @author onlineGenerator
 * @date 2018-10-17 09:42:14
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tb_user_depart", schema = "")
@SuppressWarnings("serial")
public class UserDepartEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**用户Id*/
	// @Excel(name="用户Id",width=15)
	private String userId;
	/**账簿Id*/
	// @Excel(name="账簿Id",width=15)
	private String accountbookId;
//	/**部门Id*/
//	// @Excel(name="部门Id",width=15)
	private String departDetailId;

	private DepartDetailEntity departDetail;

	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**所属部门*/
	private String sysOrgCode;
	/**所属公司*/
	private String sysCompanyCode;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

	@Column(name ="USER_ID",nullable=true,length=32)
	public String getUserId(){
		return this.userId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	@Column(name ="ACCOUNTBOOK_ID",nullable=true,length=32)
	public String getAccountbookId(){
		return this.accountbookId;
	}

	public void setAccountbookId(String accountbookId){
		this.accountbookId = accountbookId;
	}

//	@Column(name ="DEPART_DETAIL_ID",nullable=true,length=32)
	@Transient
	public String getDepartDetailId() {
		return departDetailId;
	}

	public void setDepartDetailId(String departDetailId) {
		this.departDetailId = departDetailId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depart_detail_id")
	@NotFound(action= NotFoundAction.IGNORE)
	public DepartDetailEntity getDepartDetail() {
		return departDetail;
	}

	public void setDepartDetail(DepartDetailEntity departDetail) {
		this.departDetail = departDetail;
	}

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	public void setCreateName(String createName){
		this.createName = createName;
	}

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}

	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	public void setSysCompanyCode(String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}
}