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
@Table(name = "tb_user_depart")
@SuppressWarnings("serial")
public class UserDepartEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**用户Id*/
	@ManyToOne
	@JoinColumn(name="user_id")
	private String userId;

	/**账簿Id*/
	private String accountbookId;

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
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

	@Column(name ="user_id",nullable=true,length=32)
	public String getUserId(){
		return this.userId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	@Column(name ="accountbook_id",nullable=true,length=32)
	public String getAccountbookId(){
		return this.accountbookId;
	}

	public void setAccountbookId(String accountbookId){
		this.accountbookId = accountbookId;
	}

	@Transient
	public String getDepartDetailId() {
		return departDetailId;
	}

	public void setDepartDetailId(String departDetailId) {
		this.departDetailId = departDetailId;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "depart_detail_id")
	@NotFound(action= NotFoundAction.IGNORE)
	public DepartDetailEntity getDepartDetail() {
		return departDetail;
	}

	public void setDepartDetail(DepartDetailEntity departDetail) {
		this.departDetail = departDetail;
	}

	@Column(name ="create_name",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	public void setCreateName(String createName){
		this.createName = createName;
	}

	@Column(name ="create_by",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	@Column(name ="create_date",nullable=true,length=20)
	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	@Column(name ="update_name",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}

	@Column(name ="update_by",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	@Column(name ="update_date",nullable=true,length=20)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	@Column(name ="sys_org_code",nullable=true,length=50)
	public String getSysOrgCode(){
		return this.sysOrgCode;
	}

	public void setSysOrgCode(String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}

	@Column(name ="sys_company_code",nullable=true,length=50)
	public String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	public void setSysCompanyCode(String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}
}