package com.server.tourApiProject.user;

import lombok.*;

/**
* @className : KakaoUserParams.java
* @description : 카카오회원가입 회원용 DTO
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
public class KakaoUserParams {

    private String nickName;

    private Boolean sex;

    private String birthDay;

    private String mobilePhoneNumber;

    private String email;

    private String profileImage;

    private String ageRange;

}
