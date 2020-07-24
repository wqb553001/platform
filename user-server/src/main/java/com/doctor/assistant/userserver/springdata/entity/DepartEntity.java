package com.doctor.assistant.userserver.springdata.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 部门
 * @author onlineGenerator
 * @date 2018-10-16 10:12:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tb_depart", schema = "")
@SuppressWarnings("serial")
public class DepartEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**部门编码*/
	// @Excel(name="部门编码",width=15)
	private String departCode;
	/**部门名称*/
	// @Excel(name="部门名称",width=15)
	private String departName;
	/**部门状态*/
	// @Excel(name="部门状态",width=15,dicCode="departStatus")
	private Integer departStatus;
	/**备注*/
	// @Excel(name="备注",width=15)
	private String remark;
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
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DEPART_CODE", nullable = true, length = 32)
	public String getDepartCode() {
		return this.departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}


	@Column(name = "DEPART_NAME", nullable = true, length = 100)
	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "DEPART_STATUS", nullable = true, length = 2)
	public Integer getDepartStatus() {
		return this.departStatus;
	}

	public void setDepartStatus(Integer departStatus) {
		this.departStatus = departStatus;
	}

	@Column(name = "REMARK", nullable = true, length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATE_NAME", nullable = true, length = 50)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "CREATE_BY", nullable = true, length = 50)
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "CREATE_DATE", nullable = true, length = 20)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATE_NAME", nullable = true, length = 50)
	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "UPDATE_BY", nullable = true, length = 50)
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "UPDATE_DATE", nullable = true, length = 20)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "SYS_ORG_CODE", nullable = true, length = 50)
	public String getSysOrgCode() {
		return this.sysOrgCode;
	}

	public void setSysOrgCode(String sysOrgCode) {
		this.sysOrgCode = sysOrgCode;
	}

	@Column(name = "SYS_COMPANY_CODE", nullable = true, length = 50)
	public String getSysCompanyCode() {
		return this.sysCompanyCode;
	}

	public void setSysCompanyCode(String sysCompanyCode) {
		this.sysCompanyCode = sysCompanyCode;
	}

	@Column(name = "DELETE_FLAG", columnDefinition="default 0")
	public Short getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}