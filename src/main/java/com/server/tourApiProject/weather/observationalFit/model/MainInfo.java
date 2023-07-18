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
    public String comment; // ex. 행운동, 별 보기 괜찮은 날이네요!
    @JsonProperty
    public String bestObservationalFit; // ex. 관측 적합도 ~70%
    @JsonProperty
    public String bestTime; // ex. 추천 관측시간 23시
    @JsonProperty
    public String mainEffect; // ex. 높은 광공해(이)가 관측을 방해해요
    @JsonProperty
    public Long areaId;
}
