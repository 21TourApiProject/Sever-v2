package com.server.tourApiProject.weather.observationalFit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Item {
    @JsonProperty("informCode")
    private String informCode;

    @JsonProperty("informData")
    private String informData;

    @JsonProperty("informGrade")
    private String informGrade;

    @JsonProperty("dataTime")
    private String dataTime;
}
