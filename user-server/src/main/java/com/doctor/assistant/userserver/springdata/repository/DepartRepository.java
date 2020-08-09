package com.doctor.assistant.userserver.springdata.repository;

import com.doctor.assistant.userserver.springdata.entity.DepartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartRepository extends JpaRepository<DepartEntity, String> {

    List<DepartEntity> findAll();
}
