package com.server.tourApiProject.weather.hourObserveFidelity;

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
@Api(tags = {"7.3 날씨 - 시간별 관측적합도"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor


public class HourObserveFidelityController {

    private final HourObserveFidelityService hourObserveFidelityService;

    @ApiOperation(value = "시간별 관측적합도 조회(단일)", notes = "해당 시간의 관측적합도를 조회한다")
    @GetMapping(value = "hourObserveFidelity/{date}/{hour}")
    public Integer getHourObserveFidelity(@PathVariable("date") String date, @PathVariable("hour") Integer hour) {
        return hourObserveFidelityService.getHourObserveFidelity(date, hour);
    }

    @ApiOperation(value = "시간별 관측적합도 조회(리스트)", notes = "해당 시간으로부터 익일 06시까지의 관측적합도를 조회한다")
    @GetMapping(value = "hourObserveFidelity/list/{date}/{hour}")
    public List<HourObserveFidelityParams> getHourObserveFidelityList(@PathVariable("date") String date, @PathVariable("hour") Integer hour) {
        return hourObserveFidelityService.getHourObserveFidelityList(date, hour);
    }

}
