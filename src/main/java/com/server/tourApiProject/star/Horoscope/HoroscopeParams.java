package com.server.tourApiProject.star.Horoscope;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
* @className : HoroscopeParams.java
* @description : 별자리 운세 param 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class HoroscopeParams {
    private String horImage;
    private String horEngTitle;
    private String horKrTitle;
    private String horPeriod;
    private String horDesc;
    private String horGuard;
    private String horPersonality;
    private String horTravel;
}
