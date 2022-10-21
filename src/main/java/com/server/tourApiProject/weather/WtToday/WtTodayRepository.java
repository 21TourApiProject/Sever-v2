package com.server.tourApiProject.weather.WtToday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface WtTodayRepository extends JpaRepository<WtToday, Long> {
    /**
     * 쿼리명: findByTodayWtId
     * 설명: id로 오늘의 날씨 정보 조회
     * parameter type = String
     * result type = WtToday
     */
    WtToday findByTodayWtId(@Param("todayWtId") int todayWtId);
}
