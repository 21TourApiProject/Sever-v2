package com.server.tourApiProject.weather.hourObserveFidelity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HourObserveFidelityRepository extends JpaRepository<HourObserveFidelity, Long> {


    /**
     * 쿼리명: findByDateAndHour
     * 설명: 날짜, 시간으로 시간별 관측적합도 조회
     * parameter type = String, Integer
     * result type = HourObserveFidelity
     */
    HourObserveFidelity findByDateAndHour(@Param("date") String date, @Param("hour") Integer hour);

    /**
     * 쿼리명: findByDate
     * 설명: 날짜로 시간별 관측적합도 조회
     * parameter type = String
     * result type = HourObserveFidelity
     */
    List<HourObserveFidelity> findByDate(@Param("date") String date);
}
