package com.server.tourApiProject.myWish;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"1.3 내 찜"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

/**
 * @className : MyWishController.java
 * @description : MyWish controller 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class MyWishController {
    private final MyWishService myWishService;

    @ApiOperation(value = "내 찜 입력", notes = "찜한 것(관측지 또는 관광지 또는 게시물)의 정보를 입력한다")
    @PostMapping(value = "myWish/{userId}/{itemId}/{wishType}")
    public void createMyWish(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId, @PathVariable("wishType") Integer wishType){
        myWishService.createMyWish(userId, itemId, wishType);
    }

    @ApiOperation(value = "내 찜 조회", notes = "해당하는 아이템에 대해 사용자가 찜을 했는지 조회한다.")
    @GetMapping(value = "myWish/{userId}/{itemId}/{wishType}")
    public Boolean isThereMyWish(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId, @PathVariable("wishType") Integer wishType){
        return myWishService.isThereMyWish(userId, itemId, wishType);
    }

    @ApiOperation(value = "내 찜 삭제", notes = "찜한 것(관측지 또는 관광지 또는 게시물)의 정보를 삭제한다")
    @DeleteMapping(value = "myWish/{userId}/{itemId}/{wishType}")
    public void deleteMyWish(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId, @PathVariable("wishType") Integer wishType){
        myWishService.deleteMyWish(userId, itemId, wishType);
    }

    @ApiOperation(value = "내 찜 관측지 조회", notes = "해당 사용자가 찜한 모든 '관측지' 목록을 조회한다")
    @GetMapping(value = "myWish/observation/{userId}")
    public List<MyWishParams01> getMyWishObservation(@PathVariable("userId") Long userId){
        return myWishService.getMyWishObservation(userId);
    }

    @ApiOperation(value = "내 찜 관광지 조회", notes = "해당 사용자가 찜한 모든 '관광지' 목록을 조회한다")
    @GetMapping(value = "myWish/touristPoint/{userId}")
    public List<MyWishParams01> getMyWishTouristPoint(@PathVariable("userId") Long userId){
        return myWishService.getMyWishTouristPoint(userId);
    }

    @ApiOperation(value = "내 찜 게시물 조회", notes = "해당 사용자가 찜한 모든 '게시물' 목록을 조회한다")
    @GetMapping(value = "myWish/post/{userId}")
    public List<MyWishParams2> getMyWishPost(@PathVariable("userId") Long userId){
        return myWishService.getMyWishPost(userId);
    }

    @ApiOperation(value = "내 찜 3개 조회", notes = "해당 사용자가 최근에 찜한 것(최대 3개)을 조회한다")
    @GetMapping(value = "myWish/3/{userId}")
    public List<MyWishParams3> getMyWish3(@PathVariable("userId") Long userId){
        return myWishService.getMyWish3(userId);
    }

    @ApiOperation(value = "찜개수 초기화" , notes = "게시글과 관측지의 찜한 개수를 업데이트 한다.")
    @GetMapping(value = "myWish/updateSaved")
    public Integer updateSavedCount(){
        return myWishService.updateSavedCount();
    }
}
