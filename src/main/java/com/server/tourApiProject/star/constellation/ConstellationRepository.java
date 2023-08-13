package com.server.tourApiProject.star.constellation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConstellationRepository extends JpaRepository< Constellation, Long> {

    /**
     * 쿼리명: findByStartDate1LessThanEqualAndEndDate1GreaterThanEqual
     * 설명: startDate보다 작거나 같고 endDate보다 크거나 같은 별자리 조회
     * parameter type = String
     * result type = List<Constellation>
     */
    List<Constellation> findByStartDate1LessThanEqualAndEndDate1GreaterThanEqual(@Param("startDate1") String startDate1, @Param("endDate1") String endDate1);

    List<Constellation> findByStartDate2LessThanEqualAndEndDate2GreaterThanEqual(@Param("startDate2") String startDate2, @Param("endDate2") String endDate2);

    /**
     * 쿼리명: findByConstName
     * 설명: 별자리 이름으로 별자리 조회
     * parameter type = String
     * result type = Constellation
     */
    Constellation findByConstName(@Param("constName") String constName);

    /**
     * 쿼리명: ConstellationRepository.java
     * 설명: 별자리 검색 필터
     * parameter type = String, String
     * result type = List<Constellation>
     */
    List<Constellation> findByConstNameContainingOrConstEngContaining(@Param("constName") String constName,@Param("constEng") String constEng);
}

