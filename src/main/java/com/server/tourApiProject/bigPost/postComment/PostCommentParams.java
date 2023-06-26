package com.server.tourApiProject.bigPost.postComment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.tourApiProject.user.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * className : com.server.tourApiProject.bigPost.postComment
 * description : 설명
 * modification : 2023-01-25(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-01-25
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-01-25       jinhyeok       최초생성
 */
public class PostCommentParams {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate yearDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime time;

    private String comment;

    private Long userId;

}
