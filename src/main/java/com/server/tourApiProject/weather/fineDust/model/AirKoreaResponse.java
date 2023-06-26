package com.server.tourApiProject.weather.fineDust.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AirKoreaResponse {

    @JsonProperty("response")
    private Response response;
}

