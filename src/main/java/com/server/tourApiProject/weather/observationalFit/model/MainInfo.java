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
    public String comment;
    @JsonProperty
    public String bestObservationalFit;
    @JsonProperty
    public String bestTime;
    @JsonProperty
    public Long areaId;
}
