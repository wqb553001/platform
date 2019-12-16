package com.doctor.assistant.ImageRecognition.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Invoice {
    private String invoiceNum;
    private String sellerName;
    private String sellerBank;
    private String checker;
    private String noteDrawer;
    private String purchaserName;
    private String invoiceTypeOrg;
    private String purchaserBank;
    private String remarks;
    private String password;
    private String sellerAddress;
    private String purchaserAddress;
    private String invoiceCode;
    private String payee;
    private String purchaserRegisterNum;
    private String totalAmount;
    private String amountInWords;
    private String amountInFiguers;
    private String invoiceType;
    private String sellerRegisterNum;
    private String totalTax;
    private String checkCode;
    private Date invoiceDate;
    private List<Element> commodityTaxRate;
    private List<Element> commodityAmount;
    private List<Element> commodityNum;
    private List<Element> commodityUnit;
    private List<Element> commodityPrice;
    private List<Element> commodityName;
    private List<Element> commodityType;



}

