package com.server.tourApiProject.interestArea.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class InterestAreaDetailWeatherInfo {

    public String bestDay; // 오늘 or 내일
    public Integer bestHour; // 최고 관측적합도 시간
    public Integer bestObservationalFit; // 최고 관측적합도
    public String weatherReport; // 날씨 요약 레포트
}
