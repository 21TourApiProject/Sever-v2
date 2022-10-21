package com.server.tourApiProject.touristPoint.touristDataHashTag;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = {"5.5 관광지-해시태그"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

/**
 * @className : TouristDataHashTagController.java
 * @description : TouristDataHashTag Controller 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class TouristDataHashTagController {

    private final TouristDataHashTagService touristDataHashTagService;

    @ApiOperation(value = "관광지 해시태그 조희", notes = "관광지 해시태그 정보를 조희한다")
    @GetMapping(value = "touristDataHashTag/{contentId}")
    public List<String> getTouristDataHashTag(@PathVariable("contentId") Long contentId){
        return touristDataHashTagService.getTouristDataHashTag(contentId);
    }
}
