package com.server.tourApiProject.subBanner;

import com.server.tourApiProject.alarm.Alarm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"13.1 서브 배너"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor
/**
 * className : com.server.tourApiProject.subBanner
 * description : 서브 배너 컨트롤러
 * modification : 2023-08-15(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-08-15
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------ㅠ
 * 2023-08-15       jinhyeok       최초생성
 */


public class SubBannerController {
    private final SubBannerService subBannerService;

    @ApiOperation(value = "가장 최근 서브 배너 조회", notes = "제일 최근에 생선된 서브 배너를 조회한다")
    @GetMapping(value = "lastSubBanner/")
    public SubBanner getLastSubBanner(){ return subBannerService.getLastSubBanner(); }

    @ApiOperation(value = "서브 배너 입력", notes = "서브 배너 정보를 입력한다")
    @PostMapping(value = "subBanner/")
    public void createSubBanner(@RequestBody SubBanner subBanner){
        subBannerService.createSubBanner(subBanner);
    }
}
