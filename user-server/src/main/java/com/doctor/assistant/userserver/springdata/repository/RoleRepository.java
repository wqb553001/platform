package com.doctor.assistant.userserver.springdata.repository;

import com.doctor.assistant.userserver.springdata.entity.TSRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<TSRole, String>,CrudRepository<TSRole, String> {
    TSRole findFirstByRoleName(String roleName);
}
