package com.server.tourApiProject.weather.area;

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
@Table(name = "weather_area")
public class WeatherArea {

    /**
     * 실시간 api 호출을 위한 엔티티 (5085개)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long areaId;

    @Column(nullable = false)
    private String SD; // 시도

    @Column
    private String SD2; // 영동, 영서, 경기북부, 경기남부 (미세먼지 값 매핑을 위한 값)

    @Column
    private String EMD1;

    @Column
    private String EMD2;

    @Column
    private String EMD3;

    @Column(nullable = false)
    private Double latitude; // 위도

    @Column(nullable = false)
    private Double longitude; // 경도

    @Column(nullable = false)
    private Double lightPollution; // 광공해
}
