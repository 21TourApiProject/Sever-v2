package com.server.tourApiProject.search;

import com.server.tourApiProject.bigPost.post.PostParams6;
import com.server.tourApiProject.bigPost.post.PostService;
import com.server.tourApiProject.observation.ObservationServiceImpl;
import com.server.tourApiProject.touristPoint.touristData.TouristDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @className : SearchController.java
* @description : 검색결과 컨트롤러
* @modification : 2022-08-29 (gyul chyoung) 주석추가
* @author : gyul chyoung
* @date : 2022-08-29
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-29       gyul chyoung       주석추가
 */

@Slf4j
@Api(tags = {"8.1 검색결과"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class SearchController {

    private final ObservationServiceImpl observationServiceImpl;
    private final TouristDataService touristDataService;
    private final PostService postService;

    @ApiOperation(value = "관측지 검색결과 ", notes = "검색어와 필터로 관측지 검색결과를 조회한다")
    @PostMapping(value = "search/observation/{pageNo}")
    public List<SearchParams1> getObservationWithFilter(@RequestBody SearchKey searchKey, @PathVariable(required = false) int pageNo){
        Pageable pageable =PageRequest.of(pageNo, 10, Sort.Direction.ASC, "observationId");
        return observationServiceImpl.getObservationWithFilter(searchKey.getFilter(), searchKey.getKeyword(),pageable);
    }

    @ApiOperation(value = "관광지 검색결과 ", notes = "검색어와 필터로 관광지 검색결과를 조회한다")
    @PostMapping(value = "search/touristPoint")
    public List<SearchParams1> getTouristPointWithFilter(@RequestBody SearchKey searchKey){
        return touristDataService.getTouristPointWithFilter(searchKey.getFilter(), searchKey.getKeyword());
    }
    @ApiOperation(value = "게시물 정보 필터로 조회", notes = "필터로 걸러진 게시물을 조회한다")
    @PostMapping(value = "search/post")
    public List<SearchParams1 > getPostWithFilter(@RequestBody SearchKey searchKey){
        return postService.getPostDataWithFilter(searchKey.getFilter(),searchKey.getKeyword());
    }

    @ApiOperation(value = "관광지 검색 지도결과 ", notes = "검색어와 필터로 지도를 위한 관광지 검색결과를 조회한다")
    @PostMapping(value = "search/touristPointForMap")
    public List<SearchParams1> getTouristPointWithFilterForMap(@RequestBody SearchKey searchKey){
        return touristDataService.getTouristPointWithFilterForMap(searchKey.getFilter(), searchKey.getKeyword());
    }

    @ApiOperation(value = "관측지 검색결과수", notes = "검색어와 필터로 관측지 검색 수를 조회한다")
    @PostMapping(value = "search/observation/count")
    public Long getObservationCountWithFilter(@RequestBody SearchKey searchKey){
        return observationServiceImpl.getCountWithFilter(searchKey.getFilter(), searchKey.getKeyword());
    }
}
