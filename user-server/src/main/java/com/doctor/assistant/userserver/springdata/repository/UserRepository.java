package com.doctor.assistant.userserver.springdata.repository;

import com.doctor.assistant.userserver.springdata.entity.TSUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<TSUser, String>{

    @Query(nativeQuery=true, value = "SELECT u.*,bu.* FROM t_s_user u LEFT JOIN t_s_base_user bu ON u.id = bu.id LIMIT ?")
    List<TSUser> findByLimit(int limitNum);

    List<TSUser> findByUserName(String userName);

    TSUser findByEmpNo(String empNo);

    // 根据角色，查询用户
    @Query("SELECT u FROM TSUser u INNER JOIN TSRoleUser ru ON u.id = ru.TSUser.id LEFT JOIN TSRole r ON ru.TSRole.id = r.id WHERE r.roleName = ?1")
    List<TSUser> findByRoleName(@Param("roleName")String roleName);
}
