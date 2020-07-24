package com.doctor.assistant.userserver.springdata.repository;

import com.doctor.assistant.userserver.springdata.entity.TSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EAGERUserRepository extends JpaRepository<TSUser, String> {
}
