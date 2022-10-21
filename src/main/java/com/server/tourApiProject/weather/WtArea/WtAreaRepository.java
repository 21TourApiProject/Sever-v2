package com.server.tourApiProject.weather.WtArea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface WtAreaRepository extends JpaRepository<WtArea, Long> {

    /**
     * 쿼리명: findByCityNameAndProvName
     * 설명: 시 이름과 구 이름으로 날씨 정보 조회
     * parameter type = String
     * result type = WtArea
     */
    WtArea findByCityNameAndProvName(@Param("cityName") String cityName, @Param("provName") String provName);
}
