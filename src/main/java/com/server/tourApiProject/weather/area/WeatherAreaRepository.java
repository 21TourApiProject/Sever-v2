package com.server.tourApiProject.weather.area;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherAreaRepository extends JpaRepository<WeatherArea, Long> {
    WeatherArea findBySDAndEMD(String SD, String EMD);
    List<WeatherArea> findBySGG(String SGG);
    List<WeatherArea> findBySD(String SD);
}
