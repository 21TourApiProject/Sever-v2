package com.server.tourApiProject.observation.observeHashTag;

import lombok.*;

/**
* @className : ObserveHashTagParams.java
* @description : 관측지 해쉬태그 이름 DTO
* @modification : 2022-08-29 (gyul chyoung) 주석수정
* @author : gyul chyoung
* @date : 2022-08-29
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-29       gyul chyoung       주석수정
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObserveHashTagParams {

    private String hashTagName;
}
