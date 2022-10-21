package com.server.tourApiProject.bigPost.postHashTag;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
* @className : PostHashTagParams.java
* @description : 게시물 해시태그 Param 입니다.
* @modification : 2022-08-08(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-08
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-08       jinhyeok       주석 수정

 */
public class PostHashTagParams {
    private String hashTagName;
}
