package com.server.tourApiProject.myWish;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : MyWishParams01.java
 * @description : 찜한 관측지 및 관광지 조회용 MyWish param 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class MyWishParams01 {

    private Long itemId; //관측지id 또는 관광지 id
    private String thumbnail; //썸네일
    private String title; //제목
    private String address; //주소
    private String cat3Name; //분류 ex) 카페, 해변..0
    private String overviewSim; //짧은 개요
    private List<String> hashTagNames; //해시태그 배열
}
