package com.doctor.assistant.ImageRecognition.service;

import com.alibaba.fastjson.JSON;
import com.doctor.assistant.ImageRecognition.dao.ElementDao;
import com.doctor.assistant.ImageRecognition.dao.InvoiceMainDaoI;
import com.doctor.assistant.ImageRecognition.entity.Element;
import com.doctor.assistant.ImageRecognition.entity.Invoice;
import com.doctor.assistant.ImageRecognition.entity.InvoiceMain;
import com.doctor.assistant.ImageRecognition.utils.HttpPostUtil;
import com.doctor.assistant.ImageRecognition.utils.Results;
import com.doctor.assistant.ImageRecognition.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

@Service
public class ImageRecognitionService {

    @Autowired
    InvoiceMainDaoI invoiceMainDao;
    @Autowired
    ElementDao elementDao;

    public void getAccessToken(String url, Map<String, String> headerMap, Map<String, String> paramMap){
        HttpPostUtil.doGet(url, headerMap, paramMap);
    }

    public String callBaiduImageRecognition(String url, Map<String, String> headerMap, Map<String, String> paramMap){
        return HttpPostUtil.doPost(url, headerMap, paramMap);
    }

    public String examineImage(InvoiceMain invoiceMain){
        return HttpPostUtil.doPost("url", null, null);
    }

    public InvoiceMain explainJsonToEntity(String jsonStr){
        InvoiceMain invoiceMain = JSON.toJavaObject(JSON.parseObject(jsonStr), InvoiceMain.class);
        if(invoiceMain == null) return null;
        Invoice wordsResult = invoiceMain.getWordsResult();
        if(wordsResult == null) return invoiceMain;
        wordsResult.setItems(this.countItems(wordsResult));
        return invoiceMain;
    }

    public Results insertInvoiceMain(InvoiceMain invoiceMain){
        String commodityTaxRates = UUIDUtil.getUUID();
        String commodityAmounts = UUIDUtil.getUUID();
        String commodityNums = UUIDUtil.getUUID();
        String commodityUnits = UUIDUtil.getUUID();
        String commodityPrices = UUIDUtil.getUUID();
        String commodityNames = UUIDUtil.getUUID();
        String commodityTypes = UUIDUtil.getUUID();
        Invoice wordsResult = invoiceMain.getWordsResult();

        wordsResult.setCommodityTaxRates(commodityTaxRates);
        wordsResult.setCommodityAmounts(commodityAmounts);
        wordsResult.setCommodityNums(commodityNums);
        wordsResult.setCommodityUnits(commodityUnits);
        wordsResult.setCommodityPrices(commodityPrices);
        wordsResult.setCommodityNames(commodityNames);
        wordsResult.setCommodityTypes(commodityTypes);
        // 保存 票据主体信息
        invoiceMainDao.save(invoiceMain);

        List<Element> resultElements = new ArrayList<>(14);
        // 分别保存 元数据
        List<Element> commodityTaxRate = wordsResult.getCommodityTaxRate();
        List<Element> commodityAmount = wordsResult.getCommodityAmount();
        List<Element> commodityNum = wordsResult.getCommodityNum();
        List<Element> commodityUnit = wordsResult.getCommodityUnit();
        List<Element> commodityPrice = wordsResult.getCommodityPrice();
        List<Element> commodityName = wordsResult.getCommodityName();
        List<Element> commodityType = wordsResult.getCommodityType();

        this.setElements(resultElements, commodityTaxRate, commodityTaxRates);
        this.setElements(resultElements, commodityAmount, commodityAmounts);
        this.setElements(resultElements, commodityNum, commodityNums);
        this.setElements(resultElements, commodityUnit, commodityUnits);
        this.setElements(resultElements, commodityPrice, commodityPrices);
        this.setElements(resultElements, commodityName, commodityNames);
        this.setElements(resultElements, commodityType, commodityTypes);

        this.elementDao.batchInsertElementList(resultElements);
        return Results.OK;
    }

    private int countItems(Invoice wordsResult){
        if(wordsResult == null) return 0;
        int commodityAmountSize = this.getSize(wordsResult.getCommodityAmount()),
                commodityNameSize = this.getSize(wordsResult.getCommodityName()),
                commodityTypeSize = this.getSize(wordsResult.getCommodityType()),
                commodityUnitSize = this.getSize(wordsResult.getCommodityUnit()),
                commodityNumSize = this.getSize(wordsResult.getCommodityNum()),
                commodityTaxSize = this.getSize(wordsResult.getCommodityTax()),
                CommodityPriceSize = this.getSize(wordsResult.getCommodityPrice()),
                commodityTaxRateSize = this.getSize(wordsResult.getCommodityTaxRate());

        List<Integer> list = Arrays.asList(commodityAmountSize, commodityNameSize,
                commodityNumSize, CommodityPriceSize,
                commodityTaxSize, commodityTaxRateSize,
                commodityTypeSize, commodityUnitSize);

        //求其最大值
        return list.stream().reduce(BinaryOperator.maxBy(Integer::compare)).orElseGet(() -> {return 0;});
    }

    private int getSize(List<Element> commoditys){
        if(commoditys == null) return 0;
        return commoditys.size();
    }

    private List<Element> setElements(List<Element> resultElements, List<Element> elements, String commodityId){
        if(elements.isEmpty()){
            for (Element element : elements) {
                element.setCommodityId(commodityId);
                resultElements.add(element);
            }
        }
        return resultElements;
    }

    public Results insertInvoice(){

        return Results.OK;
    }

}
