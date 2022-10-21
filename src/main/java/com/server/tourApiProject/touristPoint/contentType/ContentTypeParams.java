package com.server.tourApiProject.touristPoint.contentType;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : ContentTypeParams.java
 * @description : ContentType Param 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class ContentTypeParams {
    String code1;
    String name1;
    String code2;
    String name2;
    String code3;
    String name3;
}
