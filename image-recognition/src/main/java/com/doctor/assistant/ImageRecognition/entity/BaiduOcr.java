package com.doctor.assistant.ImageRecognition.entity;

import com.doctor.assistant.ImageRecognition.config.ApplicationConfig;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tb_baidu_ocr")
public class BaiduOcr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;
    @Column(name = "api_key")
    private String  apiKey;
    @Column(name = "api_addr")
    private String  apiAddr;
    @Column(columnDefinition="INT default 0", nullable=false)
    private int  enable;
    private String  application;
    @Column(columnDefinition="varchar(50) default 'dev'",nullable=false)
    private String  profile;
    @Column(columnDefinition="varchar(50) default 'master'",nullable=false)
    private String  label;
    @Column(name = "create_datetime", columnDefinition = "datetime default null")
    private Date createDatetime;
    @Column(name = "update_datetime", columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private Date  updateDatetime;

    public BaiduOcr() {
        this.application = ApplicationConfig.applicationName;
    }
}
