package com.server.tourApiProject.weather.observationalFit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Daily {

    @JsonProperty("dt")
    private String dt;

//    @JsonProperty("sunrise")
//    private String sunrise;
//
//    @JsonProperty("sunset")
//    private String sunset;
//
//    @JsonProperty("moonrise")
//    private String moonrise;
//
//    @JsonProperty("moonset")
//    private String moonset;

    @JsonProperty("moon_phase")
    private Double moonPhase;

//    @JsonProperty("temp")
//    private Temp temp;
//
//    @JsonProperty("feels_like")
//    private FeelsLike feelsLike;
//
//    @JsonProperty("pressure")
//    private String pressure;
//
//    @JsonProperty("humidity")
//    private String humidity;
//
//    @JsonProperty("dew_point")
//    private String dewPoint;
//
//    @JsonProperty("wind_speed")
//    private Double windSpeed;
//
//    @JsonProperty("wind_deg")
//    private Double windDeg;
//
//    @JsonProperty("wind_gust")
//    private Double windGust;
//
//    @JsonProperty("weather")
//    private List<Weather> weather;
//
//    @JsonProperty("clouds")
//    private String clouds;
//
//    @JsonProperty("pop")
//    private String pop;
//
//    @JsonProperty("rain")
//    private Double rain;
//
//    @JsonProperty("snow")
//    private Double snow;
//
//    @JsonProperty("uvi")
//    private String uvi;
}
