package com.server.tourApiProject.weather.observation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = {"7.2 날씨 - 관측지"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

public class WeatherObservationController {
    private final WeatherObservationService weatherObservationService;

    @ApiOperation(value = "관측지 조회", notes = "모든 관측지를 조회한다")
    @GetMapping(value = "observation")
    public List<WeatherObservation> getAllObservation() {
        return weatherObservationService.getAllObservation();
    }

    @ApiOperation(value = "광공해 조회", notes = "해당 관측지의 광공해 정보를 조회한다")
    @GetMapping(value = "observation/lightPollution/{latitude}/{longitude}")
    public Double getLightPollution(@PathVariable Double latitude, @PathVariable Double longitude) {
        return weatherObservationService.getLightPollution(latitude, longitude);
    }

}
