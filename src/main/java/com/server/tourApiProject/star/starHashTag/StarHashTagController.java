package com.server.tourApiProject.star.starHashTag;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"6.3 별자리 해시태그"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor
/**
 * className : com.server.tourApiProject.star.starHashTag
 * description : 별자리 해시태그 Controller
 * modification : 2022-12-27(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2022-12-27
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2022-12-27       jinhyeok       최초생성
 */
public class StarHashTagController {
    private final StarHashTagService starHashTagService;

    @ApiOperation(value = "별자리 해시태그 리스트 입력", notes = "별자리 해시태그 정보를 입력한다")
    @PostMapping(value = "starHashTag/{constId}")
    public void createStarHashTags(@PathVariable("constId") Long constId, @RequestBody List<StarHashTagParams> starHashTagParams){
        starHashTagService.createStarHashTags(constId,starHashTagParams);
    }
    @ApiOperation(value = "별자리 해시태그 리스트 조회", notes = "별자리 id로 해시태그를 조회한다.")
    @GetMapping(value = "starHashTag/{constId}")
    public List<StarHashTag> getStarHashTags(@PathVariable("const")Long constId){
        return starHashTagService.getStarHashTag(constId);
    }

    @ApiOperation(value = "별자리 해시태그 리스트 이름 조회", notes = "별자리 id로 해시태그 이름을 조회한다.")
    @GetMapping(value = "starHashTagName/{constId}")
    public List<String> getStarHashTagName(@PathVariable("constId")Long constId){
        return starHashTagService.getStarHashTagName(constId);
    }
    @ApiOperation(value = "별자리 해시태그 정보 삭제", notes = "모든 별자리 해시태그를 삭제한다")
    @DeleteMapping(value = "postHashTag/")
    public void deletePostHashTag(){
        starHashTagService.deleteStarHashTags();
    }
}
