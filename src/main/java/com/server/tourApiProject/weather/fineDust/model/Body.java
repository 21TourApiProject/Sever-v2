package com.server.tourApiProject.weather.fineDust.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Body {

    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("items")
    private List<Item> items;
}
