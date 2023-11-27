package com.server.tourApiProject.observation.model;

import lombok.*;

import javax.persistence.Column;

/**
* @className : ObservationParams.java
* @description : 관측지 생성에 사용하는 DTO
* @modification : 2022-08-27 (gyul chyoung) 주석생성
* @author : gyul chyoung
* @date : 2022-08-27
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-27       gyul chyoung       최초생성
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObservationParams {

    private String observationName;

    private String link;

    private Double latitude;

    private Double longitude;

    private String address;

    private String phoneNumber;

    private String operatingHour;

    private String entranceFee;

    private String parking; //주차안내

    private String parkingImg;  //주차안내 사진진

    private String intro;   //한줄소개

    private String observeType;    //관측지 타입(천문대,등등), 추후 enum으로 수정가능?

    private String outline; //개요

    private String reserve; //

}
