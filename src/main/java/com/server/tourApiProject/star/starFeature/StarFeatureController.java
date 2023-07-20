package com.server.tourApiProject.star.starFeature;

import com.server.tourApiProject.hashTag.HashTag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"6.4 별자리 특성"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor

/**
 * className : com.server.tourApiProject.star.starFeature
 * description : 설명
 * modification : 2023-07-06(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-07-06
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-07-06       jinhyeok       최초생성
 */
public class StarFeatureController {

    private final StarFeatureService starFeatureService;

    @ApiOperation(value = "모든 별자리 특성 조회", notes = "모든 별자리 특성을 조회한다")
    @GetMapping(value = "starFeatures/")
    public List<StarFeature> getStarFeatures(){ return starFeatureService.getAllStarFeature(); }

    @ApiOperation(value = "별자리 특성 입력", notes = "별자리 특성 정보를 입력한다")
    @PostMapping(value = "starFeature")
    public void createStarFeature(@RequestBody StarFeature starFeature){
        starFeatureService.createStarFeature(starFeature);
    }
}
