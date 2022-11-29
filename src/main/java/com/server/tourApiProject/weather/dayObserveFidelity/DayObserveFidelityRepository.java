package com.server.tourApiProject.weather.dayObserveFidelity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface DayObserveFidelityRepository extends JpaRepository<DayObserveFidelity, Long> {


    /**
     * 쿼리명: findByDate
     * 설명: 날짜로 날짜별 관측적합도 조회
     * parameter type = String
     * result type = DayObserveFidelity
     */
    DayObserveFidelity findByDate(@Param("date") String date);
}
