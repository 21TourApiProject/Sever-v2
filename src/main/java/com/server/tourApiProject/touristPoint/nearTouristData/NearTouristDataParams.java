package com.server.tourApiProject.touristPoint.nearTouristData;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : NearTouristDataParams.java
 * @description : NearTouristData Param 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class NearTouristDataParams {

    private Long contentId;
    private String firstImage;
    private String title;
    private String addr;
    private String cat3Name;
    private String overviewSim;
    private List<String> hashTagNames;
}
