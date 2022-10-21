package com.server.tourApiProject.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : UserParams.java
 * @description : 사용자 Param 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class UserParams {

    private String realName;

    private Boolean sex;

    private String birthDay;

    private String mobilePhoneNumber;

    private String email;

    private String password;

    private Boolean isMarketing;

    private Boolean kakao;

}
