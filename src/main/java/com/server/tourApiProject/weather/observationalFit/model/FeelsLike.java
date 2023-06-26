package com.server.tourApiProject.weather.observationalFit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FeelsLike {

    @JsonProperty("day")
    private Double day;
    @JsonProperty("night")
    private Double night;
    @JsonProperty("eve")
    private Double eve;
    @JsonProperty("morn")
    private Double morn;
}
