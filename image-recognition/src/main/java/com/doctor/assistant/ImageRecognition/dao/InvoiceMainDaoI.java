package com.doctor.assistant.ImageRecognition.dao;

import com.doctor.assistant.ImageRecognition.entity.InvoiceMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceMainDaoI extends JpaRepository<InvoiceMain, String> {
}
