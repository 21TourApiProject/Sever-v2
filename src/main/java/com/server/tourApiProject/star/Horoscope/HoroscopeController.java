package com.server.tourApiProject.star.Horoscope;

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
@Api(tags = {"6.2 별자리 운세"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor

/**
* @className : HoroscopeController.java
* @description : 별자리 운세 controller 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class HoroscopeController {
    private final HoroscopeService horoscopeService;

    @ApiOperation(value = "당일 날짜의 달로 별자리 정보 조회", notes = "당일 날짜의 달로 별자리 정보를 조회한다")
    @GetMapping(value = "horoscope/{todayMonth}")
    public List<HoroscopeParams> getHoroscopes(@PathVariable("todayMonth") int todayMonth) {
        return horoscopeService.getHoroscopes(todayMonth);
    }

    @ApiOperation(value = "모든 별자리 운세 조회", notes = "모든 별자리 운세를 조회한다")
    @GetMapping(value = "horoscopes")
    public List<Horoscope> getAllHoroscope() {
        return horoscopeService.getAllHoroscopes();
    }
}
