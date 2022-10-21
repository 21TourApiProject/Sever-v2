package com.server.tourApiProject.weather.WtToday;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"7.2 날씨 - 오늘의 날씨 이름 조회"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

/**
* @className : WtTodayController.java
* @description : 오늘의 날씨 controller 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class WtTodayController {
    private final WtTodayService wtTodayService;

    @ApiOperation(value = "id로 날씨 이름 조회", notes = "id로 해당 날씨의 정보를 조회한다")
    @GetMapping(value = "wtToday/{todayWtId}")
    public WtTodayParams getTodayWeatherInfo(@PathVariable("todayWtId") int todayWtId) {
        return wtTodayService.getTodayWeatherInfo(todayWtId);
    }
}
