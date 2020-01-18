package com.doctor.assistant.ImageRecognition.entity;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_invoice")
@Data
public class Invoice implements Serializable {
    @Id
    private String id;
    private String checker;
    private String remarks;
    @Column(name = "i_password")
    private String password;
    private String payee;
    @Column(name = "amount_in_words")
    private String amountInWords;
    @Column(name = "amount_in_figuers")
    private String amountInFiguers;
    @Column(name = "check_code")
    private String checkCode;
    @Column(name = "invoice_code")
    private String invoiceCode;
    @Column(name = "invoice_date")
    private Date invoiceDate;
    @Column(name = "invoice_num")
    private String invoiceNum;
    @Column(name = "invoice_type")
    private String invoiceType;
    @Column(name = "invoice_type_org")
    private String invoiceTypeOrg;
    @Column(name = "purchaser_address")
    private String purchaserAddress;
    @Column(name = "purchaser_bank")
    private String purchaserBank;
    @Column(name = "purchaser_name")
    private String purchaserName;
    @Column(name = "purchaser_register_num")
    private String purchaserRegisterNum;
    @Column(name = "seller_address")
    private String sellerAddress;
    @Column(name = "seller_bank")
    private String sellerBank;
    @Column(name = "seller_name")
    private String sellerName;
    @Column(name = "seller_register_num")
    private String sellerRegisterNum;
    @Column(name = "total_amount")
    private Double totalAmount;
    @Column(name = "total_tax")
    private String totalTax;
    @Column(name = "note_drawer")
    private String noteDrawer;

    @Column(name = "commodity_tax_rates")
    private String commodityTaxRates;
    @Column(name = "commodity_taxs")
    private String commodityTaxs;
    @Column(name = "commodity_amounts")
    private String commodityAmounts;
    @Column(name = "commodity_nums")
    private String commodityNums;
    @Column(name = "commodity_units")
    private String commodityUnits;
    @Column(name = "commodity_prices")
    private String commodityPrices;
    @Column(name = "commodity_names")
    private String commodityNames;
    @Column(name = "commodity_types")
    private String commodityTypes;

    private int items;
    @Transient
    @OneToMany
    @JoinTable(name = "tb_element",
                joinColumns = {@JoinColumn(name = "commodity_id") })
//                ,
//                inverseJoinColumns = { @JoinColumn(name = "commodity_tax_rates") }
//    @JoinColumn(name = "commodity_id")
    @Column(name = "commodity_tax_rates")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Element> commodityTaxRate;
    @OneToMany
    @JoinColumn(name = "commodity_id")
    @Column(name = "commodity_taxs")
    @NotFound(action = NotFoundAction.IGNORE)
    @Transient
    private List<Element> CommodityTax;
    @OneToMany
    @JoinColumn(name = "commodity_id")
    @Column(name = "commodity_amounts")
    @NotFound(action = NotFoundAction.IGNORE)
    @Transient
    private List<Element> commodityAmount;
    @OneToMany
    @JoinColumn(name = "commodity_id")
    @Column(name = "commodity_nums")
    @NotFound(action = NotFoundAction.IGNORE)
    @Transient
    private List<Element> commodityNum;
    @OneToMany
    @JoinColumn(name = "commodity_id")
    @Column(name = "commodity_units")
    @NotFound(action = NotFoundAction.IGNORE)
    @Transient
    private List<Element> commodityUnit;
    @OneToMany
    @Column(name = "commodity_prices")
    @JoinColumn(name = "commodity_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @Transient
    private List<Element> commodityPrice;
    @OneToMany
    @JoinColumn(name = "commodity_id")
    @Column(name = "commodity_names")
    @NotFound(action = NotFoundAction.IGNORE)
    @Transient
    private List<Element> commodityName;
    @OneToMany
    @JoinColumn(name = "commodity_id")
    @Column(name = "commodity_types")
    @NotFound(action = NotFoundAction.IGNORE)
    @Transient
    private List<Element> commodityType;

    public Invoice setCommodityTaxRates(String commodityTaxRates){
        this.commodityTaxRates = commodityTaxRates;
        return this;
    }

    public Invoice setCommodityTaxs(String commodityTaxs){
        this.commodityTaxs = commodityTaxs;
        return this;
    }

    public Invoice setCommodityAmounts(String commodityAmounts){
        this.commodityAmounts = commodityAmounts;
        return this;
    }

    public Invoice setCommodityNums(String commodityNums){
        this.commodityNums = commodityNums;
        return this;
    }

    public Invoice setCommodityUnits(String commodityUnits){
        this.commodityUnits = commodityUnits;
        return this;
    }

    public Invoice setCommodityPrices(String commodityPrices){
        this.commodityPrices = commodityPrices;
        return this;
    }

    public Invoice setCommodityNames(String commodityNames){
        this.commodityNames = commodityNames;
        return this;
    }

    public Invoice setCommodityTypes(String commodityTypes){
        this.commodityTypes = commodityTypes;
        return this;
    }

}

