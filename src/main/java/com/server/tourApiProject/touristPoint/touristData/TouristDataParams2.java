package com.server.tourApiProject.touristPoint.touristData;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : TouristDataParams2.java
 * @description : 관광지의 음식 정보 조회용 TouristData Param 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class TouristDataParams2 {

    private Long contentTypeId; //39
    private String firstImage; //대표이미지 원본
    private String title; //제목
    private String cat3Name; //소분류 이름
    private String overview; //개요
    private String addr; //주소
    private String tel; //전화번호
    private String openTimeFood; //영업시간(음식)
    private String restDateFood; //휴무일(음식)
    private String firstMenu; //대표메뉴(음식)
    private String treatMenu; //취급메뉴(음식)
    private String packing; //포장(음식)
    private String parkingFood; //주차시설(음식)
    private Double mapX; //경도
    private Double mapY; //위도
    private String overviewSim; //한줄소개
}
