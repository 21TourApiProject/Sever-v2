package com.server.tourApiProject.bigPost.postHashTag;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"2.2 게시물 해시태그"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
/**
* @className : PostHashTagController.java
* @description : 게시물 해시태그 controller 입니다.
* @modification : 2022-08-08(jinhyeok) 주석수정
* @author : jinhyeok
* @date : 2022-08-08
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-08       jinhyeok       주석 수정

 */
public class PostHashTagController {
    private final PostHashTagService postHashTagService;

    @ApiOperation(value = "게시물 해시태그 리스트 입력", notes = "게시물 해시태그 정보를 입력한다")
    @PostMapping(value = "postHashTag/{postId}")
    public void createPostHashTags(@PathVariable("postId") Long postId, @RequestBody List<PostHashTagParams> postHashTagParams){
        postHashTagService.createPostHashTags(postId,postHashTagParams);
    }
    @ApiOperation(value = "게시물 해시태그 리스트 조회", notes = "게시물 id로 해시태그를 조회한다.")
    @GetMapping(value = "postHashTag/{postId}")
    public List<PostHashTag> getPostHashTags(@PathVariable("postId")Long postId){
        return postHashTagService.getPostHashTag(postId);
    }

    @ApiOperation(value = "게시물 해시태그 리스트 이름 조회", notes = "게시물 id로 해시태그 이름을 조회한다.")
    @GetMapping(value = "postHashTagName/{postId}")
    public List<String> getPostHashTagName(@PathVariable("postId")Long postId){
        return postHashTagService.getPostHashTagName(postId);
    }
    @ApiOperation(value = "게시물 해시태그 정보 삭제", notes = "모든 게시물 해시태그를 삭제한다")
    @DeleteMapping(value = "postHashTag/")
    public void deletePostHashTag(){
        postHashTagService.deletePostHashTags();
    }
}
