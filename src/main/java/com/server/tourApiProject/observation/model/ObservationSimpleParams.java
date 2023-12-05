package com.server.tourApiProject.observation.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

/**
* @className : ObservationSimpleParams.java
* @description : 관측지 간단결과 DTO
* @modification : 2023-11-12 (gyul chyoung) 주석추가
* @author : gyul chyoung
* @date : 2022-08-29
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-11-12       gyul chyoung       생성
 */

@Builder
@NoArgsConstructor
@Getter
@Setter
public class ObservationSimpleParams {
    //관측지 간단결과에 쓸 관측지 params
    private Long itemId; //관측지id
    private String thumbnail; //썸네일
    private String title; //제목
    private String address; //주소
    private String intro; //짧은 개요
    private Double longitude;  //경도
    private Double latitude; //위도
    private Double light;   //광공해
    private Double observeFit;   //관측적합도
    private Long saved; //저장횟수


    @QueryProjection
    public ObservationSimpleParams(Long itemId, String thumbnail, String title, String address, String intro, Double longitude, Double latitude, Double light, Double observeFit, Long saved) {
        this.itemId = itemId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.address = address;
        this.intro = intro;
        this.longitude = longitude;
        this.latitude = latitude;
        this.light = light;
        this.observeFit = observeFit;
        this.saved = saved;
    }
}
