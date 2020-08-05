package com.doctor.assistant.ImageRecognition.dao;

import com.doctor.assistant.ImageRecognition.entity.Element;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@MapperScan("com.doctor.assistant.ImageRecognition.dao")
//@Transactional(value = "transactionManager")
@Mapper
@Component
public interface ElementDao {
//    @Query("select e from Element e where e.commodityId = ?1")
    List<Element> findElementByCommodityId(String commodityId);

    void batchInsertElementList(@Param("elementList") List<Element> elementList);
}