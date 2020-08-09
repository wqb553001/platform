package com.doctor.assistant.userserver.springdata.repository;

import com.doctor.assistant.userserver.springdata.entity.DepartDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartDetailRepository extends JpaRepository<DepartDetailEntity, String> {

    List<DepartDetailEntity> findAll();

    List<DepartDetailEntity> findByAccountbookId(String accountbookId);

    List<DepartDetailEntity> findByAccountbookIdAndId(String accountbookId, String id);
}
