package com.server.tourApiProject.bigPost.postComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * className : com.server.tourApiProject.bigPost.postComment
 * description : 게시글 댓글 Repository
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
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findByPostId (@Param("postId") Long postId);
    List<PostComment> findByUserId (@Param("userId") Long userId);
}
