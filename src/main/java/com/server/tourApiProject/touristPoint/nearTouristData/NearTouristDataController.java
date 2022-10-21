package com.server.tourApiProject.touristPoint.nearTouristData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"5.4 관광지-주변 관광 정보"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

/**
 * @className : NearTouristDataController.java
 * @description : NearTouristData Controller 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class NearTouristDataController {

    private final NearTouristDataService nearTouristDataService;

    @ApiOperation(value = "주변 관광지 정보 입력", notes = "주변 관광지 정보를 입력한다")
    @PostMapping(value = "nearTouristData/{contentId1}/{contentId2}")
    public void createNearTouristData(@PathVariable("contentId1") Long contentId1, @PathVariable("contentId2") Long contentId2){
        nearTouristDataService.createNearTouristData(contentId1, contentId2);
    }

    @ApiOperation(value = "주변 관광지 정보 조회", notes = "주변 관광지 정보를 조회한다")
    @GetMapping(value = "nearTouristData/{contentId}")
    public List<NearTouristDataParams> getNearTouristData(@PathVariable("contentId") Long contentId){
        return nearTouristDataService.getNearTouristData(contentId);
    }

    @ApiOperation(value = "관광지 삭제", notes = "모든 주변 관광지를 삭제한다")
    @DeleteMapping(value = "nearTouristData/touristPoint")
    public void deleteNearTouristPoint(){
        nearTouristDataService.deleteNearTouristPoint();
    }
}
