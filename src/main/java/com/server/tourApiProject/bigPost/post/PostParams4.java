package com.server.tourApiProject.bigPost.post;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
* @className : PostParams4.java
* @description : 메인 페이지 게시물 Param 입니다.
* @modification : 2022-08-08(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-08
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-08       jinhyeok       주석 수정

 */
public class PostParams4 {
    private Long postId;
    private Long observationId;
    private String mainObservation;
    private String optionObservation;
    private String mainTitle;
    private String mainNickName;
    private ArrayList<String> images;
    private List<String> hashTags;
    private String optionHashTag;
    private String optionHashTag2;
    private String optionHashTag3;
    private String profileImage;
}
