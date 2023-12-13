package com.server.tourApiProject.bigPost.post;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 쿼리명: PostRepository.java
 * 설명: 게시물 repository
 * parameter type =Post, Long
 * result type = List <Post>
 */
public interface PostRepository extends JpaRepository <Post, Long>, PostRepositoryCustom {
    List<Post> findByUserId(@Param("userId") Long userId);
    List<Post> findByObservationId(@Param("ObservationId")Long observationId);
    List<Post> findByPostTitleContainingOrPostContentContaining(@Param("postTitle") String postTitle,@Param("postContent") String postContent);
    List<Post> findByAreaCode(@Param("areaCode") Long areaCode);
    List<Post> findByPostTitleContaining(@Param("postTitle") String postTitle);


}
