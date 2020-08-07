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

    @EntityGraph(value = "base_user.Graph",type = EntityGraph.EntityGraphType.FETCH)
    List<TSUser> findAll();

    List<TSUser> findByUserName(String userName);

    TSUser findByEmpNo(String empNo);

    // 根据角色，查询用户
    @Query("SELECT u from TSUser u inner join TSRoleUser ru ON u.id = ru.TSUser.id left join TSRole r ON ru.TSRole.id = r.id where r.roleName = ?1")
    List<TSUser> findByRoleName(@Param("roleName")String roleName);
}
