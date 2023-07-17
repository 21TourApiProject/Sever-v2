package com.server.tourApiProject.bigPost.postComment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * className : com.server.tourApiProject.bigPost.postComment
 * description : 내가 쓴 댓글 페이지에서 보일 댓글 아이템
 * modification : 2023-07-16(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-07-16
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-07-16       jinhyeok       최초생성
 */
public class PostCommentParams2 {

    private Long commentId;

    private Long postId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate yearDate;

    private String comment;

    private String thumbnail;

    private String postTitle;
}
