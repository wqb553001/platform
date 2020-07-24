package com.doctor.assistant.userserver.springdata.repository;

import com.doctor.assistant.userserver.springdata.entity.DepartDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EAGERDepartDetailRepository extends JpaRepository<DepartDetailEntity, String> {
}
