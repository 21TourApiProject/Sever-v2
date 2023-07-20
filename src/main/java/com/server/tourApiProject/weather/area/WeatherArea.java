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
    private String EMD; // 읍면동

    @Column(nullable = false)
    private Double latitude; // 위도

    @Column(nullable = false)
    private Double longitude; // 경도

    @Column(nullable = false)
    private String SGG; // 시군구

    @Column(nullable = false)
    private String sigungu; // 시군구 (검색용. 추구 필드명 변경)

    @Column
    private String SGG2; // 영동/영서, 경기북부/경기남부

    @Column(nullable = false)
    private Double lightPollution; // 광공해
}
