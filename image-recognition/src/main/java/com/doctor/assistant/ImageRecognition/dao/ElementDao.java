package com.doctor.assistant.ImageRecognition.dao;

import com.doctor.assistant.ImageRecognition.entity.Element;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ElementDao {
//    @Query("select e from Element e where e.commodityId = ?1")
    List<Element> findElementByCommodityId(String commodityId);

    void batchInsertElementList(@Param("elementList") List<Element> elementList);
}