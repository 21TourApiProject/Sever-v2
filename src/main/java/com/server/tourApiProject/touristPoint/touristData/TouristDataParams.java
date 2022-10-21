package com.server.tourApiProject.touristPoint.touristData;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : TouristDataParams.java
 * @description : 관광지 정보 조회용 TouristData Param 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class TouristDataParams {

    private Long contentTypeId; //12
    private String firstImage; //대표이미지 원본
    private String title; //제목
    private String cat3Name; //소분류 이름
    private String overview; //개요
    private String addr; //주소
    private String tel; //전화번호
    private String useTime; //이용시간(관광지)
    private String restDate; //휴무일(관광지)
    private String expGuide ; //체험안내(관광지)
    private String parking; //주차시설(관광지)
    private String chkPet; //반려동물(관광지)
    private String homePage; //홈페이지(관광지)
    private Double mapX; //경도
    private Double mapY; //위도
    private String overviewSim; //한줄소개
}
