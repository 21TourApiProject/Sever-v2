package com.server.tourApiProject.weather.observationalFit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpenWeatherResponse {

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lon")
    private String lon;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("timezone_offset")
    private Integer timezoneOffset;

    @JsonProperty("hourly")
    private List<Hourly> hourly;

    @JsonProperty("daily")
    private List<Daily> daily;

}
