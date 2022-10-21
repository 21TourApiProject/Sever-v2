package com.server.tourApiProject.weather.WtArea;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"7.1 날씨 - 지역 경도위도 조회"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

/**
* @className : WtAreaController.java
* @description : 날씨 controller 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class WtAreaController {
    private final WtAreaService wtAreaService;

    @ApiOperation(value = "지역명으로 경도, 위도, 광공해 조회", notes = "해당 지역의 경도, 위도, 광공해 정보를 조회한다")
    @GetMapping(value = "wtAreas/{cityName}/{provName}")
    public WtAreaParams getAreaInfo(@PathVariable("cityName") String cityName, @PathVariable("provName") String provName) {
        return wtAreaService.getAreaInfo(cityName, provName);
    }
}
