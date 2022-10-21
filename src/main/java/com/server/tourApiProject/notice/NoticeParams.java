package com.server.tourApiProject.notice;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : NoticeParams.java
 * @description : Notice params 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class NoticeParams {
    private String noticeTitle;
    private String noticeContent;
    private String noticeDate;
}
