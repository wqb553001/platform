package com.doctor.assistant.userserver.springdata.repository;

import com.doctor.assistant.userserver.springdata.entity.DepartDetailEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartDetailRepository extends JpaRepository<DepartDetailEntity, String> {

    @Override
    @EntityGraph(value = "depart_detail.Graph",type = EntityGraph.EntityGraphType.FETCH)
    List<DepartDetailEntity> findAll();
}
