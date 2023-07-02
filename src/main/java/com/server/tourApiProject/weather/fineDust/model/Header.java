package com.server.tourApiProject.weather.fineDust.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Header {

    @JsonProperty("resultMsg")
    private String resultMsg;

    @JsonProperty("resultCode")
    private String resultCode;

}
