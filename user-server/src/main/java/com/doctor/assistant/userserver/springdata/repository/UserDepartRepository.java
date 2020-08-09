package com.doctor.assistant.userserver.springdata.repository;

import com.doctor.assistant.userserver.springdata.entity.UserDepartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDepartRepository extends JpaRepository<UserDepartEntity, String>, CrudRepository<UserDepartEntity, String> {

    List<UserDepartEntity> findAll();

    // ① DepartDetailId 与 ② DepartDetail_Id 是有区别的 ② 代表 DepartDetail.id
    List<UserDepartEntity> findUserDepartEntitiesByAccountbookIdAndDepartDetail_Id(String accountbookId, String departDetailId);

}
