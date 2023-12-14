package com.server.tourApiProject.observation;

import com.server.tourApiProject.observation.course.CourseService;
import com.server.tourApiProject.observation.model.ObservationParams;
import com.server.tourApiProject.observation.model.ObservationSimpleParams;
import com.server.tourApiProject.observation.observeFee.ObserveFee;
import com.server.tourApiProject.observation.observeFee.ObserveFeeService;
import com.server.tourApiProject.observation.observeHashTag.ObserveHashTagService;
import com.server.tourApiProject.observation.observeImage.ObserveImageParams2;
import com.server.tourApiProject.observation.observeImage.ObserveImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @className : ObservationController.java
* @description : 관측지 컨트롤러
* @modification : 2022-08-27 (gyul chyoung)
* @author : gyul chyoung
* @date : 2022-08-27  
* @version : 1.0 
     ====개정이력(Modification Information)====      
  수정일        수정자        수정내용    ----------------------------------------- 
   2022-08-27       gyul chyoung       주석최초생성
 */

@Slf4j
@Api(tags = {"4.1 관측지"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor
public class ObservationController {
    private final ObservationService observationService;
    private final ObserveHashTagService observeHashTagService;
    private final ObserveImageService observeImageService;
    private final ObserveFeeService observeFeeService;
    private final CourseService courseService;


    @ApiOperation(value = "모든 관측지 조회", notes = "모든 관측지를 조회한다")
    @GetMapping(value = "observations")
    public List<Observation> getAllObservation(){ return observationService.getAllObservation(); }

    @ApiOperation(value = "관측지 입력", notes = "관측지 정보를 입력한다")
    @PostMapping(value = "observation")
    public void createObservation(@RequestBody ObservationParams observationParams){
        observationService.createObservation(observationParams);
    }

    @ApiOperation(value = "관측지 조회", notes = "관측지 id로 관측지를 조회한다")
    @GetMapping(value = "observation/{observationId}")
    public Observation getObservation(@PathVariable("observationId") Long observationId){
        return observationService.getObservation(observationId);
    }

    @ApiOperation(value = "관측지 이미지 경로", notes = "관측지 이미지경로를 id로 조회한다")
    @GetMapping(value = "observation/{observationId}/observeImage")
    public List<String> getObserveImagePath(@PathVariable("observationId") Long observationId){
        return observeImageService.getObserveImage(observationId);
    }

    @ApiOperation(value = "관측지 이미지 정보 조회", notes = "관측지 이미지경로와 출처를 id로 조회한다")
    @GetMapping(value = "observation/{observationId}/observeImageInfo")
    public List<ObserveImageParams2> getObserveImageInfo(@PathVariable("observationId") Long observationId){
        return observeImageService.getObserveImageInfo(observationId);
    }

    @ApiOperation(value = "관측지 해쉬태그 조회 ", notes = "관측지id로 해쉬태그를 조회한다")
    @GetMapping(value = "observation/{observationId}/observeHashTag")
    public List<String> getObserveHashTags(@PathVariable("observationId") Long observationId){
        return observeHashTagService.getObserveHashTag(observationId);
    }

    @ApiOperation(value = "관측지 입장료 조회 ", notes = "관측지id로 입장료리스트를 조회한다")
    @GetMapping(value = "observation/{observationId}/observeFee")
    public List<ObserveFee> getObserveFeeList(@PathVariable("observationId") Long observationId){
        return observeFeeService.getObserveFees(observationId);
    }

    @ApiOperation(value = "관측적합도 상위 관측지 리스트", notes = "관측적합도 상위 10개 관측지 결과를 가져온다.")
    @GetMapping(value = "observations/simple")
    public List<ObservationSimpleParams> getBestFitObservationList(){
        return observationService.getBestFitObservationList();
    }

    @ApiOperation(value = "가까운 관측지 리스트", notes = "지역에 가까운 3개 관측지 결과를 가져온다.")
    @GetMapping(value = "observations/near/{areaId}/{size}")
    public List<ObservationSimpleParams> getNearObservationIds(@PathVariable("areaId") Long areaId, @PathVariable("size") int size){
        return observationService.getNearObservationIds(areaId,size);
    }
}
