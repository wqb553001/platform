package com.doctor.assistant.userserver.springdata.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Macx
 * @Title: 账簿Entity
 * @Description: 账簿信息
 */
@Entity
@Table(name = "tb_accountbook", schema = "")
@SuppressWarnings("serial")
public class AccountbookEntity extends IdEntity implements java.io.Serializable {
    /**
     * 父账簿
     */
    private AccountbookEntity parentAccountbook;

    /**
     * 父账簿Id
     */
    // @Excel(name = "母公司", width = 15, dictTable = "tb_accountbook where delete_flag=0", dicCode = "id", dicText = "accountbook_name")
    private String parentAccountbookId;

    /**
     * 子账簿
     */
    private List<AccountbookEntity> accountbookList = new ArrayList<AccountbookEntity>();

    private String rootId;

    /**
     * 持股比例
     */
    // @Excel(name = "持股比例(%)", width = 15)
    private BigDecimal shareholdingRatio;

    /**
     * 账簿编码
     */
//    // @Excel(name = "编码", width = 15)
    private String accountbookCode;

    /**
     * 账簿名称
     */
    // @Excel(name = "账簿名称", width = 15)
    private String accountbookName;
    /**
     * 公司全称
     */
    // @Excel(name = "公司全称", width = 15)
    private String company;
    /**
     * 状态
     */
    // @Excel(name = "状态", width = 15, dicCode = "status")
    private Integer useStatus;
    /**
     * 初始会计期间
     */
    // @Excel(name = "初始会计期间(导入后不可更改)", width = 15, format = "yyyy-MM-dd")
    private Date useDateTime;
    /**
     * 成立时间
     */
    // @Excel(name = "成立时间", width = 15, format = "yyyy-MM-dd")
    private Date establishDateTime;
    /**
     * 结账月份
     */
    private String checkoutMonth;
    /**
     * 注册资本
     */
    // @Excel(name = "注册资本(元)", width = 15)
    private BigDecimal registeredCapital;
    /**
     * 法人
     */
    // @Excel(name = "法定代表人", width = 15)
    private String legalPerson;

    /**
     * 公司地址
     */
    // @Excel(name = "公司地址", width = 15)
    private java.lang.String address;
    /**
     * 纳税人性质
     *      小规模纳税人 ：1 （默认普票1）
     *      一般纳税人   ：2 （默认专票2）
     */
    // @Excel(name = "纳税人性质", width = 15, dicCode = "taxpayerType")
    private java.lang.Integer taxpayerType;
    /**
     * 报税地区
     */
    // @Excel(name = "报税地区", width = 15)
    private String taxArea;
    /**
     * 纳税人识别号
     */
    // @Excel(name = "纳税人识别号", width = 15)
    private String taxpayerCode;
    /**
     * 适用税率
     */
    // @Excel(name = "所得税税率(%)", width = 15)
    private BigDecimal taxRate;
    /**
     * 开票限额(普票)
     */
    // @Excel(name = "开票限额(普票)(元)", width = 15)
    private BigDecimal invoiceLimit;
    /**
     * 开票限额(专票)
     */
    // @Excel(name = "开票限额(专票)(元)", width = 15)
    private BigDecimal invoiceLimitSpecial;
    /**
     * 备注
     */
    // @Excel(name = "备注", width = 15)
    private String remark;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建人登录名称
     */
    private String createBy;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新人名称
     */
    private String updateName;
    /**
     * 更新人登录名称
     */
    private String updateBy;
    /**
     * 更新日期
     */
    private Date updateDate;
    /**
     * 所属公司
     */
    private String sysCompanyCode;
    /**
     * 所属部门
     */
    private String sysOrgCode;
    /**
     * 启用部门
     */
	private String departIds;
    /**
     * 增值税税率
     */
	private String vatRates;
    /**
     * 启用模块
     */
	private String moduleIds;
    /** 状态: 0:不删除  1：删除 */
    private Short deleteFlag;

	@Column(name = "ROOT_ID", nullable = true, length = 36)
	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

    @Column(name = "PARENT_ID", nullable = true, length = 36)
    public String getParentAccountbookId() {
        return parentAccountbookId;
    }

    public void setParentAccountbookId(String parentAccountbookId) {
        this.parentAccountbookId = parentAccountbookId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    @JsonBackReference
    public AccountbookEntity getParentAccountbook() {
        return parentAccountbook;
    }
    @JsonBackReference
    public void setParentAccountbook(AccountbookEntity parentAccountbook) {
        this.parentAccountbook = parentAccountbook;
    }

    @OneToMany(mappedBy = "parentAccountbook")
    @JsonManagedReference
    @NotFound(action= NotFoundAction.IGNORE)
    public List<AccountbookEntity> getAccountbookList() {
        return accountbookList;
    }
    @JsonManagedReference
    public void setAccountbookList(List<AccountbookEntity> accountbookList) {
        this.accountbookList = accountbookList;
    }

    @Column(name = "ACCOUNTBOOK_CODE", nullable = true, length = 32)
    public String getAccountbookCode() {
        return this.accountbookCode;
    }

    public void setAccountbookCode(String accountbookCode) {
        this.accountbookCode = accountbookCode;
    }

//	@Column(name ="ACCOUNTBOOK_LEVEL",nullable=true,length=32)
//	public Integer getAccountbookLevel(){
//		return this.accountbookLevel;
//	}

//	public void setAccountbookLevel(Integer accountbookLevel){
//		this.accountbookLevel = accountbookLevel;
//	}

    @Column(name = "ACCOUNTBOOK_NAME", nullable = true, length = 32)
    public String getAccountbookName() {
        return this.accountbookName;
    }

    public void setAccountbookName(String accountbookName) {
        this.accountbookName = accountbookName;
    }

    @Column(name = "COMPANY", nullable = true, length = 32)
    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "USE_STATUS", nullable = true, length = 32)
    public Integer getUseStatus() {
        return this.useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    @Column(name = "USE_DATE_TIME", nullable = true, length = 32)
    public Date getUseDateTime() {
        return this.useDateTime;
    }

    public void setUseDateTime(Date useDateTime) {
        this.useDateTime = useDateTime;
    }

    @Column(name = "ESTABLISH_DATE_TIME", nullable = true, length = 32)
    public Date getEstablishDateTime() {
        return this.establishDateTime;
    }

    public void setEstablishDateTime(Date establishDateTime) {
        this.establishDateTime = establishDateTime;
    }

    @Column(name = "CHECKOUT_MONTH")
    public String getCheckoutMonth() {
        return checkoutMonth;
    }

    public void setCheckoutMonth(String checkoutMonth) {
        this.checkoutMonth = checkoutMonth;
    }

    @Column(name = "REGISTERED_CAPITAL", nullable = true, length = 32)
    public BigDecimal getRegisteredCapital() {
        return this.registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    @Column(name = "LEGAL_PERSON", nullable = true, length = 32)
    public String getLegalPerson() {
        return this.legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    @Column(name = "SHAREHOLDING_RATIO", nullable = true, scale = 2, length = 32)
    public BigDecimal getShareholdingRatio() {
        return this.shareholdingRatio;
    }

    public void setShareholdingRatio(BigDecimal shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    @Column(name = "ADDRESS", nullable = true, length = 32)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 纳税人性质
     *      一般纳税人：1   （专票）
     *      小规模纳税人：2 （普票）
     */
    @Column(name = "TAXPAYER_TYPE", nullable = false, length = 32)
    public java.lang.Integer getTaxpayerType() {
        return this.taxpayerType;
    }

    public void setTaxpayerType(java.lang.Integer taxpayerType) {
        this.taxpayerType = taxpayerType;
    }

    @Column(name = "TAX_AREA", nullable = true, length = 32)
    public String getTaxArea() {
        return this.taxArea;
    }

    public void setTaxArea(String taxArea) {
        this.taxArea = taxArea;
    }

    @Column(name = "TAXPAYER_CODE", nullable = true, length = 32)
    public String getTaxpayerCode() {
        return this.taxpayerCode;
    }

    public void setTaxpayerCode(String taxpayerCode) {
        this.taxpayerCode = taxpayerCode;
    }

    @Column(name = "TAX_RATE", nullable = true, scale = 2, length = 32)
    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Column(name = "REMARK", nullable = true, length = 32)
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

    @Column(name = "SYS_COMPANY_CODE", nullable = true, length = 50)
    public String getSysCompanyCode() {
        return this.sysCompanyCode;
    }

    public void setSysCompanyCode(String sysCompanyCode) {
        this.sysCompanyCode = sysCompanyCode;
    }

    @Column(name = "SYS_ORG_CODE", nullable = true, length = 50)
    public String getSysOrgCode() {
        return this.sysOrgCode;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    @Column(name = "DELETE_FLAG", columnDefinition="default 0")
    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Column(name = "INVOICE_LIMIT", nullable = true, length = 50)
    public BigDecimal getInvoiceLimit() {
        return invoiceLimit;
    }

    public void setInvoiceLimit(BigDecimal invoiceLimit) {
        this.invoiceLimit = invoiceLimit;
    }

    @Column(name = "INVOICE_LIMIT_SPECIAL", nullable = true, length = 50)
    public BigDecimal getInvoiceLimitSpecial() {
        return invoiceLimitSpecial;
    }

    public void setInvoiceLimitSpecial(BigDecimal invoiceLimitSpecial) {
        this.invoiceLimitSpecial = invoiceLimitSpecial;
    }

    @Transient
    public String getDepartIds() {
        return departIds;
    }

    public void setDepartIds(String departIds) {
        this.departIds = departIds;
    }

    @Transient
    public String getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }

    @Transient
    public String getVatRates() {
        return vatRates;
    }

    public void setVatRates(String vatRates) {
        this.vatRates = vatRates;
    }
}
