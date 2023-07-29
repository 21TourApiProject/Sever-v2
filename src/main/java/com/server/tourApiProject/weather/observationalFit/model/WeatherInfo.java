package com.server.tourApiProject.weather.observationalFit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WeatherInfo {

    @JsonProperty
    public DetailWeather detailWeather;
    @JsonProperty
    public List<HourObservationalFit> hourObservationalFitList;
    @JsonProperty
    public List<DayObservationalFit> dayObservationalFitList;
    @JsonProperty
    public Integer lightPollutionLevel;
    @JsonProperty
    public String todayComment1; // 오늘 23시에
    @JsonProperty
    public String todayComment2; // 관측하면 가장 좋아요
    @JsonProperty
    public Integer bestObservationalFit;
    @JsonProperty
    public Integer bestTime;
    @JsonProperty
    public String mainEffect;

    @Setter
    @Getter
    @NoArgsConstructor
    public static class DetailWeather {
        public String weatherText;
        public String tempHighest;
        public String tempLowest;
        public String rainfallProbability;
        public String humidity;
        public String cloud;
        public String fineDust;
        public String windSpeed;
        public String moonAge;
        public String sunrise;
        public String sunset;
        public String moonrise;
        public String moonset;
    }

    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HourObservationalFit {
        public String hour;
        public String observationalFit;
    }

    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DayObservationalFit {
        public String day;
        public String date;
        public String observationalFit;
    }

}
