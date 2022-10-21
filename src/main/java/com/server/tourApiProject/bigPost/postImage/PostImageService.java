package com.server.tourApiProject.bigPost.postImage;

import com.server.tourApiProject.bigPost.post.Post;
import com.server.tourApiProject.bigPost.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
/**
* @className : PostImageService.java
* @description : 게시물 이미지 Service 입니다. (게시물 이미지 생성, 삭제/ 게시물 이미지 가져오기 / 관련 게시물 이미지 가져오기)
* @modification : 2022-08-08(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-08
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-08       jinhyeok       주석 수정

 */
public class PostImageService {
    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;


    /**
     * description: 게시물 id를 통해 게시물 이미지 가져오는 메소드 .
     *
     * @param postId - the post id
     * @return the post image
     */
    public List<String> getPostImage(Long postId) {
        List<String> postImageNameList =new ArrayList<>();
        List<PostImage> postImageList = postImageRepository.findByPostId(postId);
        for(PostImage p : postImageList) {
            postImageNameList.add(p.getImageName());
        }
        return postImageNameList;
    }
    public String getPostImageName(Long postImageListId){
        PostImage postImage = postImageRepository.findByPostImageListId(postImageListId);
        return postImage.getImageName();
    }

    /**
     *description: 게시물 이미지 생성하는 메소드.
     *
     * @param postId -  the post id
     * @param postImageParams - the post image params
     */
    public void createPostImage(Long postId,List<PostImageParams> postImageParams) {
        for (PostImageParams p : postImageParams) {
            Post post = postRepository.findById(postId).orElseThrow(IllegalAccessError::new);
            PostImage postImage = new PostImage();
            postImage.setImageName(p.getImageName());
            postImage.setPost(post);
            postImage.setPostId(post.getPostId());

            postImageRepository.save(postImage);
        }
    }

    /**
     * description: 관측지 id로 관련 게시물 이미지 리스트 가져오는 메소드.
     *
     * @param observationId the observation id
     * @return List<PostImage>
     */
    public List<PostImage> getRelatePostImageList(Long observationId){
        List<PostImage> postImageList = new ArrayList<>();
        List<Post> posts = postRepository.findByObservationId(observationId);
        for (Post post : posts){
            PostImage postImage = new PostImage();
            postImage.setPost(post);
            postImage.setPostId(post.getPostId());
            postImage.setImageName(post.getPostImages().get(0).getImageName());
            postImageList.add(postImage);
        }
        return postImageList;
    }

    /**
     * description: 게시물 이미지 삭제하는 메소드.
     */
    public void deletePostImage(){postImageRepository.deleteAll();}
}
