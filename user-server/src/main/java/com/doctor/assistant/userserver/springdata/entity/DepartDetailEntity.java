package com.doctor.assistant.userserver.springdata.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Title: Entity
 * @Description: 部门详情
 */
@Entity
@Table(name = "tb_depart_detail", schema = "")
@NamedEntityGraph(name = "depart_detail.Graph", attributeNodes = {
		@NamedAttributeNode(value = "depart")
})
@SuppressWarnings("serial")
public class DepartDetailEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**账簿Id*/
	private String accountbookId;
	/**部门*/
	private DepartEntity depart;
	/**状态*/
	private Integer status;
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
	/** 状态: 0:不删除  1：删除 */
	private Short deleteFlag;

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

	@Column(name ="ACCOUNTBOOK_ID",nullable=true,length=36)
	public String getAccountbookId(){
		return this.accountbookId;
	}

	public void setAccountbookId(String accountbookId){
		this.accountbookId = accountbookId;
	}

	@ManyToOne(targetEntity = DepartEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "depart_id")
	public DepartEntity getDepart() {
		return depart;
	}

	public void setDepart(DepartEntity depart) {
		this.depart = depart;
	}

	@Column(name ="STATUS",nullable=true,length=11)
	public Integer getStatus(){
		return this.status;
	}

	public void setStatus(Integer status){
		this.status = status;
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

	@Column(name = "DELETE_FLAG", columnDefinition="int default 0")
	public Short getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}