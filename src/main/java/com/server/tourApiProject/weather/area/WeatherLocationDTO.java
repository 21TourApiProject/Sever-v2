package com.server.tourApiProject.weather.area;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherLocationDTO {

    /**
     * 위치 검색을 위한 엔티티
     */

    private String title;

    private String subtitle;

    private Long areaId; // WEATHER_AREA id

    private Long observationId; // WEATHER_OBSERVATION id

    private Double latitude;

    private Double longitude;

    private Long observationalFit;
}
