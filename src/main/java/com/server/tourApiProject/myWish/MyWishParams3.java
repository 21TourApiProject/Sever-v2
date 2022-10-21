package com.server.tourApiProject.myWish;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : MyWishParams3.java
 * @description : 제일 최근에 찜한 조회용 MyWish param 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class MyWishParams3 {
    private String thumbnail; //썸네일
    private String title; //제목
    private Integer wishType; //0이면 관측지, 1이면 관광지, 2면 게시물
}
