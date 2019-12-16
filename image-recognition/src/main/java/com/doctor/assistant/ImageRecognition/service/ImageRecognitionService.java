package com.doctor.assistant.ImageRecognition.service;

import com.alibaba.fastjson.JSON;
import com.doctor.assistant.ImageRecognition.entity.InvoiceEn;
import com.doctor.assistant.ImageRecognition.utils.HttpPostUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageRecognitionService {

    public void getAccessToken(String url, Map<String, String> headerMap, Map<String, String> paramMap){
        HttpPostUtil.doGet(url, headerMap, paramMap);
    }

    public String callBaiduImageRecognition(String url, Map<String, String> headerMap, Map<String, String> paramMap){
        return HttpPostUtil.doPost(url, headerMap, paramMap);
    }

    public String examineImage(InvoiceEn invoiceEn){
        return HttpPostUtil.doPost("url", null, null);
    }

    public InvoiceEn explainJsonToEntity(String jsonStr){
        return JSON.toJavaObject(JSON.parseObject(jsonStr), InvoiceEn.class);
    }
}
