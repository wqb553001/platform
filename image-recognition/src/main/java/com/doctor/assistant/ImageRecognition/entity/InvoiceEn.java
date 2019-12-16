package com.doctor.assistant.ImageRecognition.entity;

import lombok.Data;

@Data
public class InvoiceEn {

    private String logId;
    private String wordsResultNum;
    private Invoice wordsResult;

}
