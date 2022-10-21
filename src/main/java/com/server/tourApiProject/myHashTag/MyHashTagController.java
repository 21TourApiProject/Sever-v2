package com.server.tourApiProject.myHashTag;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"1.2 선호 해시태그"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

/**
 * @className : MyHashTagController.java
 * @description : MyHashTag controller 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class MyHashTagController {
    private final MyHashTagService myHashTagService;

    @ApiOperation(value = "선호 해시태그 리스트 입력", notes = "선호 해시태그 정보를 입력한다")
    @PostMapping(value = "myHashTag/{email}")
    public Long createMyHashTags(@PathVariable("email") String email, @RequestBody List<MyHashTagParams> myHashTagParams){
        return myHashTagService.createMyHashTags(email, myHashTagParams);
    }
    @ApiOperation(value = "선호 해시태그 아이디 조회", notes = "사용자 id로 해당 사용자의 선호 해시태그 아이디를 조회한다")
    @GetMapping(value = "user/{userId}/myHashTagId")
    public List<Long> getMyHashTagIdList(@PathVariable("userId") Long userId){ return myHashTagService.getMyHashTagIdList(userId); }

    @ApiOperation(value = "선호 해시태그 조회", notes = "사용자 id로 해당 사용자의 선호 해시태그를 조회한다")
    @GetMapping(value = "user/{userId}/myHashTag")
    public List<String> getMyHashTag(@PathVariable("userId") Long userId){ return myHashTagService.getMyHashTag(userId); }

    @ApiOperation(value = "선호 해시태그 3개 조회", notes = "사용자 id로 해당 사용자의 선호 해시태그 3개를 조회한다")
    @GetMapping(value = "user/{userId}/myHashTag/three")
    public List<String> getMyHashTag3(@PathVariable("userId") Long userId){ return myHashTagService.getMyHashTag3(userId); }

    @ApiOperation(value = "선호 해시태그 변경", notes = "선호 해시태그를 변경한다")
    @PostMapping(value = "myHashTag/change/{userId}")
    public void changeMyHashTag(@PathVariable("userId") Long userId, @RequestBody List<MyHashTagParams> myHashTagParams){
        myHashTagService.changeMyHashTag(userId, myHashTagParams);
    }
}
