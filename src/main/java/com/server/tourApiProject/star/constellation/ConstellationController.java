package com.server.tourApiProject.star.constellation;

import com.server.tourApiProject.bigPost.post.Post;
import com.server.tourApiProject.bigPost.post.PostParams6;
import com.server.tourApiProject.search.SearchKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"6.1 별자리"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

/**
* @className : ConstellationController.java
* @description : 별자리 controller 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class ConstellationController {
    private final ConstellationService constellationService;

    @ApiOperation(value = "별자리 입력", notes = "별자리 정보를 입력한다.")
    @PostMapping(value = "constellation")
    public void createConstellation(@RequestBody Constellation constellation) {
        constellationService.createConstellation(constellation);
    }

    @ApiOperation(value = "별자리 정보 조회", notes = "별자리 아이디로 별자리를 조회한다")
    @GetMapping(value = "constellations/{constId}")
    public Constellation getConstellationById(@PathVariable("constId") Long constId) {
        return constellationService.getConstellationById(constId);
    }

    @ApiOperation(value = "모든 별자리 조회", notes = "모든 별자리를 조회한다")
    @GetMapping(value = "constellations")
    public List<ConstellationParams> getConstellation() {
        return constellationService.getConstellation();
    }

    @ApiOperation(value = "모든 별자리 이름 조회", notes = "모든 별자리 이름을 조회한다")
    @GetMapping(value = "constellations/constName")
    public List<ConstellationParams2> getConstNames() {
        return constellationService.getConstNames();
    }

    @ApiOperation(value = "당일 날짜에 보이는 별자리 조회", notes = "당일 날짜에 보이는 별자리를 조회한다")
    @GetMapping(value = "constellation/todayConst")
    public List<ConstellationParams> getTodayConst() {
        return constellationService.getTodayConst();
    }

    @ApiOperation(value = "당일 날짜에 보이는 별자리 이름 조회", notes = "당일 날짜에 보이는 별자리의 이름을 조회한다")
    @GetMapping(value = "constellation/todayConstName")
    public List<ConstellationParams2> getTodayConstName() {
        return constellationService.getTodayConstName();
    }

    @ApiOperation(value = "별자리 상세 정보 조회", notes = "별자리 이름으로 별자리 상세 정보를 조회한다.")
    @GetMapping(value = "constellation/{constName}")
    public Constellation getDetailConst(@PathVariable("constName") String constName) {
        return constellationService.getDetailConst(constName);
    }

    @ApiOperation(value = "별자리 정보 필터로 조회", notes = "필터로 걸러진 별자리을 조회한다")
    @PostMapping(value = "search/constellation")
    public List<ConstellationParams> getConstellationWithFilter(@RequestBody SearchKey searchKey){
        return constellationService.getConstDataWithFilter(searchKey.getFilter(),searchKey.getKeyword());
    }

}
