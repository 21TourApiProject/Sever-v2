package com.server.tourApiProject.bigPost.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
* @className : PostParams.java
* @description : 게시물 페이지 Param 입니다.
* @modification : 2022-08-05(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-05
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-05       jinhyeok       주석 생성

 */
public class PostParams {

    private String postContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate yearDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime time;

    private String postTitle;

    private String optionHashTag;
    private String optionHashTag2;
    private String optionHashTag3;
    private String optionHashTag4;
    private String optionHashTag5;
    private String optionHashTag6;
    private String optionHashTag7;
    private String optionHashTag8;
    private String optionHashTag9;
    private String optionHashTag10;

    private String optionObservation;

    private Long userId;
}
