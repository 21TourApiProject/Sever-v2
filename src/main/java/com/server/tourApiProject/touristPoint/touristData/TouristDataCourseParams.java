package com.server.tourApiProject.touristPoint.touristData;

import lombok.*;

/**
* @className : TouristDataCourseParams.java
* @description : 관측지 코스 조회용 관광지 DTO
* @modification : 2022-08-29 (gyul chyoung) 주석추가
* @author : gyul chyoung
* @date : 2022-08-29
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-29       gyul chyoung       주석추가
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TouristDataCourseParams {

    private Long contentTypeId; //12
    private String firstImage; //대표이미지 원본
    private String title; //제목
    private String cat3Name; //소분류 이름
    private String overview; //개요
    private String addr; //주소
    private String useTime; //이용시간(관광지)
    private String parking; //주차시설(관광지)
    private String treatMenu; //취급메뉴(음식)
}
