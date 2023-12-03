package com.server.tourApiProject.weather.observationalFit.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ObservationFitRequestDTO {

    String date; // 날짜 (2023-05-29)
    Long observationId; // OBSERVATION id
}
