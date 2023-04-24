package com.server.tourApiProject.observation;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObservationRepository extends JpaRepository<Observation, Long>, JpaSpecificationExecutor<Observation> {

    /**
     * 쿼리명: findByObservationName
     * 설명: 관측지 이름으로 관측지 조회
     * parameter type = String
     * result type = Observation
     */
    Observation findByObservationName(@Param("observationName")String ObservationName);

    /**
     * 쿼리명: findByAreaCode
     * 설명: 지역코드로 관측지 리스트 조회
     * parameter type = Long
     * result type = List<Observation>
     */
    List<Observation> findByAreaCode(@Param("areaCode") Long areaCode);

    /**
     * 쿼리명: findByObservationNameContainingOrOutlineContaining
     * 설명: 개요와 관측지 이름에 검색어 포함된 관측지 리스트 조회
     * parameter type = String, String
     * result type =  List<Observation>
     */
    List<Observation> findByObservationNameContainingOrOutlineContaining(String observationName, String outline);
//    반환형 findBy제목ContainingOr개요또는내용Containing(String 제목검색어, String 내용검색어);


//
}
