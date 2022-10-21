package com.server.tourApiProject.touristPoint.contentType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"5.2 관광지-서비스 분류"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

/**
 * @className : ContentTypeController.java
 * @description : ContentType Controller 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class ContentTypeController {

    private final ContentTypeService contentTypeService;

    @ApiOperation(value = "서비스 정보 입력", notes = "관광지 서비스 정보를 입력한다")
    @PostMapping(value = "contentType")
    public void createContentType(@RequestBody ContentType contentType){
        contentTypeService.createContentType(contentType);
    }
}
