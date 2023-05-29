package com.server.tourApiProject.weather.area;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = {"7.1 날씨 - 지역"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

public class WeatherAreaController {
    private final WeatherAreaService weatherAreaService;

    @ApiOperation(value = "광공해 조회", notes = "해당 지역의 광공해 정보를 조회한다")
    @GetMapping(value = "area/lightPollution/{latitude}/{longitude}")
    public Double getLightPollution(@PathVariable Double latitude, @PathVariable Double longitude){
        return weatherAreaService.getLightPollution(latitude, longitude);
    }

}
