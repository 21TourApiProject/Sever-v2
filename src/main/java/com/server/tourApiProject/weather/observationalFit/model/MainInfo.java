package com.server.tourApiProject.weather.observationalFit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MainInfo {

    @JsonProperty
    public String location; // ex. 행운동
    @JsonProperty
    public String comment; // ex. 오늘 날씨는 맑음, 기온은 최저 nnº, 최고 NNº에요. 월령이 낮고, 구름도 적어 별 보기 좋은 날이네요. 눈 소식에 유의하세요!
    @JsonProperty
    public Long regionId;
}
