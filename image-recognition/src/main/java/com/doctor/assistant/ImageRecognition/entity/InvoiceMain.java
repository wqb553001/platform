package com.doctor.assistant.ImageRecognition.entity;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_invoice_main")
@Data
public class InvoiceMain implements Serializable {
    @Id
    private String id;
    private String logId;
    private String wordsResultNum;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Invoice wordsResult;
}
