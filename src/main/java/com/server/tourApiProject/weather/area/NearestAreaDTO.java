package com.server.tourApiProject.weather.area;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearestAreaDTO {

    private String sgg; // 시군군 (서대문구)

    private Double latitude;

    private Double longitude;

    private String date; // 날짜 (2023-05-29)

    private Integer hour; // 현재 시간 (18시와의 차이 계산 필요)
}
