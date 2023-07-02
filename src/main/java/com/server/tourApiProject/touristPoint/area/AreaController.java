package com.server.tourApiProject.touristPoint.area;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"5.1 관광지-지역"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor

/**
 * @className : AreaController.java
 * @description : Area Controller 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class AreaController {
    private final AreaService areaService;

    @ApiOperation(value = "시군구 입력", notes = "해당 지역의 시군구 정보를 입력한다")
    @PostMapping(value = "area")
    public void createArea(@RequestBody AreaParams areaParams){
        areaService.createArea(areaParams);
    }

    @ApiOperation(value = "필터용 지역 조회", notes = "검색에 이용할 필터용 지역의 id와 이름을 조회한다.")
    @GetMapping(value = "area/filter")
    public List<AreaFilterParams> findFilterArea(){
        return areaService.findAreaFilter();
    }

}
