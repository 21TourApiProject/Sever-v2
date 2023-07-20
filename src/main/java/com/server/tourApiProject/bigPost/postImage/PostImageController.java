package com.server.tourApiProject.bigPost.postImage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"2.3 게시물 이미지"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor
/**
* @className : PostImageController.java
* @description : 게시물 이미지 Controller 입니다.
* @modification : 2022-08-08(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-08
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-08       jinhyeok       주석 수정

 */
public class PostImageController {
    private final PostImageService postImageService;

    @ApiOperation(value = "게시물 이미지 추가", notes = "게시물 이미지를 추가한다")
    @PostMapping(value = "postImage/{postId}")
    public void createPostImage(@PathVariable("postId") Long postId, @RequestBody List<PostImageParams> postImageParams){
        postImageService.createPostImage(postId,postImageParams);
    }
    @ApiOperation(value = "게시물 이미지 정보 삭제", notes = "모든 게시물 이미지를 삭제한다")
    @DeleteMapping(value = "postImage/")
    public void deletePostImage(){ postImageService.deletePostImage(); }
}
