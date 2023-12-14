package com.server.tourApiProject.bigPost.post;

import com.server.tourApiProject.bigPost.postHashTag.PostHashTag;
import com.server.tourApiProject.bigPost.postHashTag.PostHashTagService;
import com.server.tourApiProject.bigPost.postImage.PostImage;
import com.server.tourApiProject.bigPost.postImage.PostImageService;
import com.server.tourApiProject.observation.Observation;
import com.server.tourApiProject.observation.ObservationService;
import com.server.tourApiProject.observation.ObservationServiceImpl;
import com.server.tourApiProject.search.Filter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"2.1 게시물"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor

/**
* @className : PostController.java
* @description : 게시물 controller 입니다
* @modification : 2022-08-05(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-05
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-05       jinhyeok       주석 생성

 */
public class PostController {

    private final PostService postService;
    private final PostHashTagService postHashTagService;
    private final ObservationServiceImpl observationServiceImpl;
    private final PostImageService postImageService;


    @ApiOperation(value = "게시물 정보 조회", notes = "게시물 아이디로 게시물을 조회한다")
    @GetMapping(value = "post/{postId}")
    public Post getPost(@PathVariable("postId") Long postId) {
        return postService.getPost(postId);
    }

    @ApiOperation(value = "게시물정보 입력", notes = "게시물 정보를 입력한다")
    @PostMapping(value = "post/{observePointName}/{areaId}")
    public Long createPost(@PathVariable("observePointName") String observePointName,
                           @RequestBody PostParams postParams,
                           @PathVariable("areaId") Long areaId) {
        return postService.createPost(observePointName,postParams,areaId);
    }
    @ApiOperation(value = "게시물정보 삭제", notes = "게시물 정보를 삭제한다")
    @DeleteMapping(value = "post/{postId}")
    public void deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
    }

    @ApiOperation(value = "게시물 해시태그 조회", notes = "게시물 id로 해당 게시물의 게시물 해시태그를 조회한다")
    @GetMapping(value = "post/{postId}/postHashTag")
    public List<PostHashTag> getPostHashTag(@PathVariable("postId") Long postId) {
        return postHashTagService.getPostHashTag(postId);
    }

    @ApiOperation(value = "게시물 관측지 조회", notes = "관측지 id로 해당 게시물의 관측지를 조회한다")
    @GetMapping(value = "post/{observationId}/observation")
    public Observation getObservation(@PathVariable("observationId") Long observationId) {
        return observationServiceImpl.getObservation(observationId);
    }
    @ApiOperation(value = "게시물 이미지 조회", notes = "게시물 id로 해당 게시물의 게시물 이미지를 조회한다")
    @GetMapping(value = "post/{postId}/postImage")
    public List<String> getPostImage(@PathVariable("postId") Long postId) {
        return postImageService.getPostImage(postId);
    }
    @ApiOperation(value = "관련 게시물 이미지 검색", notes = "게시물 관측지 id로 이미지를 조회한다")
    @GetMapping(value = "postImage/{postObservePointId}")
    public List<PostImage> getRelatePostImageList(@PathVariable("postObservePointId") Long postObservePointId){
        return postImageService.getRelatePostImageList(postObservePointId);
    }

    @ApiOperation(value = "내 게시물 3개 정보 조회", notes = "해당 사용자의 최근 게시물(최대 3개)을 조회한다")
    @GetMapping(value = "post/3/{userId}")
    public List<PostParams2> getMyPost3(@PathVariable("userId")Long userId){ return postService.getMyPost3(userId); }

    @ApiOperation(value = "내 게시물 정보 조회", notes = "해당 사용자의 게시물을 조회한다")
    @GetMapping(value = "post/user/{userId}")
    public List<PostParams3> getMyPost(@PathVariable("userId")Long userId){ return postService.getMyPost(userId); }

    @ApiOperation(value = "관측지 게시물 정보 조회", notes = "해당 관측지의 게시물을 조회한다")
    @GetMapping(value = "post/observation/{observationId}")
    public List<PostParams5> getRelatePost(@PathVariable("observationId")Long observationId){ return postService.getRelatePost(observationId); }

    @ApiOperation(value = "관측지 게시물 정보 사이즈로 조회", notes = "해당 관측지의 게시물을 사이즈로 조회한다")
    @GetMapping(value = "post/observation/{observationId}/{size}")
    public List<PostContentsParams> getObservationPostWithSize(@PathVariable("observationId")Long observationId, @PathVariable("size")int size){ return postService.getObservationRelatePostWithSize(size, observationId); }

    @ApiOperation(value = "메인페이지 게시물 정보 조회", notes = "메인페이지에 띄울 모든 게시물을 조회한다")
    @PostMapping(value = "post/main")
    public List<PostParams4> getMainPost(){ return postService.getMainPost(); }

    @ApiOperation(value = "게시물 정보 사이즈로 조회", notes = "게시물 정보를 최신순으로 사이즈만큼 가져온다.")
    @GetMapping(value = "posts/{size}")
    public List<PostContentsParams> getLatestPostWithSize(@PathVariable("size")int size){ return postService.getLatestPostsWithSize(size); }

}

