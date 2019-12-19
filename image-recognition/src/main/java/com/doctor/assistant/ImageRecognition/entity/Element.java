package com.doctor.assistant.ImageRecognition.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_element")
@Data
public class Element implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="commodity_id")
    private String commodityId;
    private String word;
    @Column(name="row_sort")
    private int row;

}