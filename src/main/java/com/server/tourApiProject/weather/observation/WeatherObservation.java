package com.server.tourApiProject.weather.observation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "weather_observation")
public class WeatherObservation {

    /**
     * 미리 api 호출을 위한 엔티티 (122개)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long observationId;

    @Column(nullable = false)
    private String name; // 관측지 이름

    @Column(nullable = false)
    private String address; // 미세먼지 값을 위한 주소

    @Column(nullable = false)
    private Double latitude; // 위도

    @Column(nullable = false)
    private Double longitude; // 경도

    @Column(nullable = false)
    private Double lightPollution; // 광공해
}
