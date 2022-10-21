package com.server.tourApiProject.bigPost.postHashTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * 쿼리명: PostHashTagRepository.java
 * 설명: 게시물 해시태그 Repository
 * parameter type = PostHashTag, Long
 * result type = List<PostHashTag>
 */
public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {
    List<PostHashTag> findByPostId(@Param("postId") Long postId);
    List<PostHashTag> findByHashTagId(@Param("hashTagId")Long hashTagId);
}
