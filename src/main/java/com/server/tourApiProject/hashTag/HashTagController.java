package com.server.tourApiProject.hashTag;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"3.1 해시태그"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

/**
 * @className : HashTagController.java
 * @description : HashTag controller 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class HashTagController {

    private final HashTagService hashTagService;

    @ApiOperation(value = "모든 해시태그 조회", notes = "모든 해시태그를 조회한다")
    @GetMapping(value = "hashTags")
    public List<HashTag> getHashTag(){ return hashTagService.getAllHashTag(); }

    @ApiOperation(value = "해시태그 입력", notes = "해시태그 정보를 입력한다")
    @PostMapping(value = "hashTag")
    public void createHashTag(@RequestBody HashTag hashTag){
        hashTagService.createHashTag(hashTag);
    }
}
