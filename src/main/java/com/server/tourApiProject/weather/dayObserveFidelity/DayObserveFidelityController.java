package com.server.tourApiProject.weather.dayObserveFidelity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"7.4 날씨 - 날짜별 관측적합도"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor


public class DayObserveFidelityController {

    private final DayObserveFidelityService dayObserveFidelityService;

    @ApiOperation(value = "날짜별 관측적합도 조회", notes = "해당 날짜의 관측적합도를 조회한다")
    @GetMapping(value = "dayObserveFidelity/{date}")
    public Integer getDayObserveFidelity(@PathVariable("date") String date) {
        return dayObserveFidelityService.getDayObserveFidelity(date);
    }

}
