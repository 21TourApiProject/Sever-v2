package com.server.tourApiProject.weather.WtToday;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
* @className : WtTodayParams.java
* @description : 오늘의 날씨 param 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class WtTodayParams{
    private String todayWtName1;
    private String todayWtName2;
}
