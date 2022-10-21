package com.server.tourApiProject.weather.WtToday;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

/**
* @className : WtTodayService.java
* @description : 오늘의 날씨 service 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class WtTodayService {
    private final WtTodayRepository wtTodayRepository;

    /**
     * TODO 오늘의 날씨 조회
     * @param todayWtId - 오늘의 날씨 id
     * @return WtTodayParams
     */
    public WtTodayParams getTodayWeatherInfo(int todayWtId) {
        WtToday wtToday = wtTodayRepository.findByTodayWtId(todayWtId);

        if (wtToday.getTodayWtId() == todayWtId) {
            WtTodayParams wtTodayParams = new WtTodayParams();
            wtTodayParams.setTodayWtName1(wtToday.getTodayWtName1());
            wtTodayParams.setTodayWtName2(wtToday.getTodayWtName2());
            return wtTodayParams;
        }
        return WtTodayParams.builder().build();
    }
}
