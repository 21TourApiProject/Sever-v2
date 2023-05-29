package com.server.tourApiProject.weather.observationalFit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Temp {

    @JsonProperty("day")
    private String day;

    @JsonProperty("min")
    private String min;

    @JsonProperty("max")
    private String max;

}