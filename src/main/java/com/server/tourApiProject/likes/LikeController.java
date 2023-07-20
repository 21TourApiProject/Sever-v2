package com.server.tourApiProject.likes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = {"12.1 좋아요"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor

/**
 * className : com.server.tourApiProject.like
 * description : 게시글 좋아요 Controller
 * modification : 2023-02-08(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-02-08
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-02-08       jinhyeok       최초생성
 */

public class LikeController {
    private final LikeService likeService;

    @ApiOperation(value = "좋아요 누르기", notes = "좋아요 한(관측지 또는 관광지 또는 게시물)의 정보를 입력한다")
    @PostMapping(value = "like/{userId}/{itemId}/{likeType}")
    public void createLike(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId,
                           @PathVariable("likeType") Integer likeType){
        likeService.createLike(userId, itemId, likeType);
    }

    @ApiOperation(value = "내 좋아요 조회", notes = "해당 하는 아이템에 대해 사용자가 좋아요를 했는지 조회한다.")
    @GetMapping(value = "like/{userId}/{itemId}/{likeType}")
    public Boolean isThereLike (@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId,
                                @PathVariable("likeType") Integer likeType){
        return likeService.isThereLike(userId, itemId, likeType);
    }

    @ApiOperation(value = "내 좋아요 삭제", notes = "좋아요한 것(관측지 또는 관광지 또는 게시물)의 정보를 삭제한다")
    @DeleteMapping(value = "like/{userId}/{itemId}/{likeType}")
    public void deleteLike(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId,
                           @PathVariable("likeType") Integer likeType){
        likeService.deleteLike(userId, itemId, likeType);
    }
    @ApiOperation(value = "아이템 좋아요 수 조회", notes = "좋아요한 것(관측지 또는 관광지 또는 게시물)의 좋아요 수를 조회한다")
    @GetMapping(value = "like/{itemId}/{likeType}")
    public Long getLikeCount( @PathVariable("itemId") Long itemId,
                           @PathVariable("likeType") Integer likeType){
        return likeService.getLikeCount( itemId, likeType);
    }
}
