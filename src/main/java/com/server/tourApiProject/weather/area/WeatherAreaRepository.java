package com.server.tourApiProject.weather.area;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeatherAreaRepository extends JpaRepository<WeatherArea, Long> {
    WeatherArea findBySDAndEMD1(String SD, String EMD1);
    WeatherArea findBySDAndEMD2(String SD, String EMD2);
    WeatherArea findBySDAndEMD3(String SD, String EMD3);
    Optional<List<WeatherArea>> findByEMD1(String EMD1);
    Optional<List<WeatherArea>> findByEMD2(String EMD2);
    List<WeatherArea> findBySD(String SD);
}
