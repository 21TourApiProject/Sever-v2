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
    public String comment; // ex. 관측적합도 최대 98%로 별 보기 딱 좋네요! 추천 관측 시간은 NN시에요.
}
