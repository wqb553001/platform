package com.doctor.assistant.ImageRecognition.dao;

import com.doctor.assistant.ImageRecognition.entity.Element;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ElementDao {
    @Query("select e from Element e where e.commodityId = ?1")
    List<Element> findElementByCommodityId(String commodityId);

    public void batchInsertElementList(List<Element> elementList);
}