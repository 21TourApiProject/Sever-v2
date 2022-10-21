package com.server.tourApiProject.myWish;

import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : MyWishParams2.java
 * @description :  * @description : 찜한 게시물 조회용 MyWish param 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class MyWishParams2 {

    private Long itemId; //게시물 id
    private String thumbnail; //썸네일
    private String title; //제목
    private String nickName; //작성자
    private String profileImage; //프로필 사진
    private List<String> hashTagNames; //해시태그 배열
}
