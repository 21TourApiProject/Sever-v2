package com.server.tourApiProject.weather.observationalFit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Header {

    @JsonProperty("resultMsg")
    private String resultMsg;

    @JsonProperty("resultCode")
    private String resultCode;
}
