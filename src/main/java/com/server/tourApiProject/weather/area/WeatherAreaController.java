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
    @GetMapping(value = "area/lightPollution/{areaId}")
    public Double getLightPollution(@PathVariable Long areaId){
        return weatherAreaService.getLightPollution(areaId);
    }

    @ApiOperation(value = "지역 조회", notes = "해당 id 의 지역을 조회한다")
    @GetMapping(value = "area/{areaId}")
    public WeatherArea getWeatherArea(@PathVariable Long areaId){
        return weatherAreaService.getWeatherArea(areaId);
    }

}
