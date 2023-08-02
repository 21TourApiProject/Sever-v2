package com.server.tourApiProject.weather.area;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherAreaRepository extends JpaRepository<WeatherArea, Long> {
    WeatherArea findBySGGAndEMD(String SGG, String EMD);
    List<WeatherArea> findBysigungu(String sigungu);
}
