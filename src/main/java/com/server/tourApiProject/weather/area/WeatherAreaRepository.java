package com.server.tourApiProject.weather.area;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherAreaRepository extends JpaRepository<WeatherArea, Long> {
    WeatherArea findBySGGAndEMD(String SGG, String EMD);
}
