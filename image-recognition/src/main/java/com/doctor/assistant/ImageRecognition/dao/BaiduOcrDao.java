package com.doctor.assistant.ImageRecognition.dao;

import com.doctor.assistant.ImageRecognition.entity.BaiduOcr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaiduOcrDao extends JpaRepository<BaiduOcr, Integer> {
}
