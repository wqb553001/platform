package com.doctor.assistant.userserver.springdata.repository;

import com.doctor.assistant.userserver.springdata.entity.TSRoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends JpaRepository<TSRoleUser, String>, CrudRepository<TSRoleUser, String> {

}
