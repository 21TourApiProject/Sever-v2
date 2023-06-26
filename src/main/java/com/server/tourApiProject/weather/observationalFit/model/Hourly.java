package com.server.tourApiProject.weather.observationalFit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Hourly {

    //    @JsonProperty("dt")
    private String dt;

//    @JsonProperty("temp")
//    private String temp;

    @JsonProperty("feels_like")
    private Double feelsLike;

//    @JsonProperty("pressure")
//    private String pressure;
//
    @JsonProperty("humidity")
    private String humidity;
//
//    @JsonProperty("dew_point")
//    private Double dewPoint;
//
//    @JsonProperty("uvi")
//    private String uvi;

    @JsonProperty("clouds")
    private Double clouds;

//    @JsonProperty("visibility")
//    private String visibility;
//
    @JsonProperty("wind_speed")
    private Double windSpeed;
//
//    @JsonProperty("wind_deg")
//    private String windDeg;
//
//    @JsonProperty("wind_gust")
//    private Double windGust;
//
    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("pop")
    private String pop;
}
