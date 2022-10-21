package com.server.tourApiProject.bigPost.postImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 쿼리명: PostImageRepository.java
 * 설명: 게시물 이미지 Repository
 * parameter type = PostImage, Long
 * result type = List<PostImage>, PostImage
 */
public interface PostImageRepository extends JpaRepository <PostImage,Long>{
    List<PostImage> findByPostId(@Param("postId") Long postId);
    PostImage findByPostImageListId(@Param("postImageListId")Long postImageListId);
}
