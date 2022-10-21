package com.server.tourApiProject.touristPoint.touristData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.touristPoint.nearTouristData.NearTouristData;
import com.server.tourApiProject.touristPoint.touristDataHashTag.TouristDataHashTag;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : TouristData.java
 * @description : 관광지 엔티티 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
@Table(name="touristData")
public class TouristData {

    @Id
    private Long contentId; //콘텐츠 ID

    @Column(nullable = false)
    private Integer isCom; //정보가 다 들어왔는지 0이면 x 1이면 o

    @Column(nullable = false)
    private Integer isJu; //주변 정보가 들어왔는지 0이면 x 1이면 o

    @Column(nullable = false)
    private Integer isIm; //기본 이미지 정보가 없으면 0, 있으면 1, 추가 이미지 정보가 있으면 2

    @JsonIgnore
    @OneToMany(mappedBy = "touristData", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TouristDataHashTag> touristDataHashTags = new ArrayList<>();

    @Column
    private String addr; //주소

    @Column
    private Long areaCode; //지역 코드

    @Column
    private Long sigunguCode; //시군구 코드

    @Column
    private String cat1; //대분류 코드

    @Column
    private String cat2; //중분류 코드

    @Column
    private String cat3; //소분류 코드

    @Column(nullable = false)
    private Long contentTypeId; //콘텐츠타입 ID

    @Column
    private String firstImage; //대표이미지 원본

    @Column
    private Double mapX; //GPS X좌표

    @Column
    private Double mapY; //GPS Y좌표

    @Column
    private String tel; //전화번호

    @Column
    private String title; //제목

    @Column(columnDefinition = "TEXT")
    private String overview; //개요

    @Column
    private String overviewSim; //짧은 개요

    @Column(columnDefinition = "TEXT")
    private String homePage; //홈페이지(관광지)

    @Column(columnDefinition = "TEXT")
    private String useTime; //이용시간(관광지)

    @Column
    private String restDate; //휴무일(관광지)

    @Column(columnDefinition = "TEXT")
    private String expGuide ; //체험안내(관광지)

    @Column(columnDefinition = "TEXT")
    private String parking; //주차시설(관광지)

    @Column
    private String chkPet; //반려동물(관광지)

    @Column
    private String openTimeFood; //영업시간(음식)

    @Column
    private String restDateFood; //휴무일(음식)

    @Column
    private String firstMenu; //대표메뉴(음식)

    @Column(columnDefinition = "TEXT")
    private String treatMenu; //취급메뉴(음식)

    @Column
    private String packing; //포장(음식)

    @Column
    private String parkingFood; //주차시설(음식)

    @JsonIgnore
    @OneToMany(mappedBy = "touristData", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<NearTouristData> myNearTouristData = new ArrayList<>();

}
