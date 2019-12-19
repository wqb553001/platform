package com.doctor.assistant.ImageRecognition.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "tb_invoice")
@Data
public class Invoice implements Serializable {
    @Id
    private String id;
    private String invoiceNum;
    private String sellerName;
    private String sellerBank;
    private String checker;
    private String noteDrawer;
    private String purchaserName;
    private String invoiceTypeOrg;
    private String purchaserBank;
    private String remarks;
    @Column(name = "i_password")
    private String password;
    private String sellerAddress;
    private String purchaserAddress;
    private String invoiceCode;
    private String payee;
    private String purchaserRegisterNum;
    private Double totalAmount;
    private String amountInWords;
    private String amountInFiguers;
    private String invoiceType;
    private String sellerRegisterNum;
    private String totalTax;
    private String checkCode;
    private Date invoiceDate;

    private String commodityTaxRates;
    private String CommodityTaxs;
    private String commodityAmounts;
    private String commodityNums;
    private String commodityUnits;
    private String commodityPrices;
    private String commodityNames;
    private String commodityTypes;

    private int items;
    @Transient
    private Map<String, Element> commodityTaxRateMap;
    @Transient
    private List<Element> commodityTaxRate;
    @Transient
    private List<Element> CommodityTax;
    @Transient
    private List<Element> commodityAmount;
    @Transient
    private List<Element> commodityNum;
    @Transient
    private List<Element> commodityUnit;
    @Transient
    private List<Element> commodityPrice;
    @Transient
    private List<Element> commodityName;
    @Transient
    private List<Element> commodityType;

}

