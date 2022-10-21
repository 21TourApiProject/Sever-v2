package com.server.tourApiProject.observation.observeImage;

import lombok.*;

/**
* @className : ObserveImageParams2.java
* @description : 관측지 이미지 DTO
* @modification : 2022-08-29 (gyul chyoung) 주석추가
* @author : gyul chyoung
* @date : 2022-08-29
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-29       gyul chyoung       주석추가
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObserveImageParams2 {
    private String image;
    private String imageSource;
}
