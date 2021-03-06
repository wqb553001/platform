package com.doctor.assistant.userserver.springdata.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Macx
 * @Title: 账簿Entity
 * @Description: 账簿信息
 */
@Entity
@Table(name = "tb_accountbook")
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
    private Set<AccountbookEntity> accountbookSet = new HashSet<>();

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

	@Column(name = "root_id", nullable = true, length = 36)
	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

    @Column(name = "parent_id", nullable = true, length = 36)
    public String getParentAccountbookId() {
        return parentAccountbookId;
    }

    public void setParentAccountbookId(String parentAccountbookId) {
        this.parentAccountbookId = parentAccountbookId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    @JsonBackReference
    public AccountbookEntity getParentAccountbook() {
        return parentAccountbook;
    }
    @JsonBackReference
    public void setParentAccountbook(AccountbookEntity parentAccountbook) {
        this.parentAccountbook = parentAccountbook;
    }

    @OneToMany(mappedBy = "parentAccountbook", fetch = FetchType.EAGER)
    @JsonManagedReference
    @NotFound(action= NotFoundAction.IGNORE)
    public Set<AccountbookEntity> getAccountbookSet() {
        return accountbookSet;
    }
    @JsonManagedReference
    public void setAccountbookSet(Set<AccountbookEntity> accountbookSet) {
        this.accountbookSet = accountbookSet;
    }

    @Column(name = "accountbook_code", nullable = true, length = 32)
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

    @Column(name = "accountbook_name", nullable = true, length = 32)
    public String getAccountbookName() {
        return this.accountbookName;
    }

    public void setAccountbookName(String accountbookName) {
        this.accountbookName = accountbookName;
    }

    @Column(name = "company", nullable = true, length = 32)
    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "use_status", nullable = true, length = 32)
    public Integer getUseStatus() {
        return this.useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    @Column(name = "use_date_time", nullable = true, length = 32)
    public Date getUseDateTime() {
        return this.useDateTime;
    }

    public void setUseDateTime(Date useDateTime) {
        this.useDateTime = useDateTime;
    }

    @Column(name = "establish_date_time", nullable = true, length = 32)
    public Date getEstablishDateTime() {
        return this.establishDateTime;
    }

    public void setEstablishDateTime(Date establishDateTime) {
        this.establishDateTime = establishDateTime;
    }

    @Column(name = "checkout_month")
    public String getCheckoutMonth() {
        return checkoutMonth;
    }

    public void setCheckoutMonth(String checkoutMonth) {
        this.checkoutMonth = checkoutMonth;
    }

    @Column(name = "registered_capital", nullable = true, length = 32)
    public BigDecimal getRegisteredCapital() {
        return this.registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    @Column(name = "legal_person", nullable = true, length = 32)
    public String getLegalPerson() {
        return this.legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    @Column(name = "shareholding_ratio", nullable = true, scale = 2, length = 32)
    public BigDecimal getShareholdingRatio() {
        return this.shareholdingRatio;
    }

    public void setShareholdingRatio(BigDecimal shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio;
    }

    @Column(name = "address", nullable = true, length = 32)
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
    @Column(name = "taxpayer_type", nullable = false, length = 32)
    public java.lang.Integer getTaxpayerType() {
        return this.taxpayerType;
    }

    public void setTaxpayerType(java.lang.Integer taxpayerType) {
        this.taxpayerType = taxpayerType;
    }

    @Column(name = "tax_area", nullable = true, length = 32)
    public String getTaxArea() {
        return this.taxArea;
    }

    public void setTaxArea(String taxArea) {
        this.taxArea = taxArea;
    }

    @Column(name = "taxpayer_code", nullable = true, length = 32)
    public String getTaxpayerCode() {
        return this.taxpayerCode;
    }

    public void setTaxpayerCode(String taxpayerCode) {
        this.taxpayerCode = taxpayerCode;
    }

    @Column(name = "tax_rate", nullable = true, scale = 2, length = 32)
    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Column(name = "remark", nullable = true, length = 32)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "create_name", nullable = true, length = 50)
    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Column(name = "create_by", nullable = true, length = 50)
    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "create_date", nullable = true, length = 20)
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "update_name", nullable = true, length = 50)
    public String getUpdateName() {
        return this.updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "update_by", nullable = true, length = 50)
    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "update_date", nullable = true, length = 20)
    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "sys_company_code", nullable = true, length = 50)
    public String getSysCompanyCode() {
        return this.sysCompanyCode;
    }

    public void setSysCompanyCode(String sysCompanyCode) {
        this.sysCompanyCode = sysCompanyCode;
    }

    @Column(name = "sys_org_code", nullable = true, length = 50)
    public String getSysOrgCode() {
        return this.sysOrgCode;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    @Column(name = "delete_flag", columnDefinition="default 0")
    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Column(name = "invoice_limit", nullable = true, length = 50)
    public BigDecimal getInvoiceLimit() {
        return invoiceLimit;
    }

    public void setInvoiceLimit(BigDecimal invoiceLimit) {
        this.invoiceLimit = invoiceLimit;
    }

    @Column(name = "invoice_limit_special", nullable = true, length = 50)
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
