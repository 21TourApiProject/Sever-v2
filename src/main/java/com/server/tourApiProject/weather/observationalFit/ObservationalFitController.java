package com.server.tourApiProject.weather.observationalFit;

import com.server.tourApiProject.weather.area.NearestAreaDTO;
import com.server.tourApiProject.weather.observationalFit.model.AreaTimeDTO;
import com.server.tourApiProject.weather.observationalFit.model.MainInfo;
import com.server.tourApiProject.weather.observationalFit.model.WeatherInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Api(tags = {"7.3 날씨 - 관측적합도"})
@RestController
@RequestMapping(value = "/v2/weather")
@RequiredArgsConstructor
public class ObservationalFitController {

    private final ObservationalFitService observationalFitService;

    /**
     * 하루의 시간별 관측적합도(익일 18-23, 명일 0-6)를 조회하여 db에 저장
     *
     * @param date 날짜 (2023-05-29)
     */
    @PostMapping("/observationalFit/save/{date}")
    public void setObservationFit(@PathVariable String date) {
        observationalFitService.setObservationFit(date);
    }

    /**
     * 앱 관측적합도 상세 페이지를 위한 실시간 날씨 정보 제공
     */
    @PostMapping("/observationalFit/weatherPage")
    public Mono<WeatherInfo> getWeatherInfo(@RequestBody AreaTimeDTO areaTime) {
        return observationalFitService.getWeatherInfo(areaTime);
    }

    /**
     * 앱 메인 페이지 인근 지역 날씨 제공
     * input : 시군구 (서대문구, 세종), 위도, 경도
     */
    @ApiOperation(value = "현위치 날씨 조회", notes = "위도/경도를 기준으로 가장 가까운 읍면동을 찾고, 해당 지역의 날씨를 구한다")
    @PostMapping(value = "observationalFit/nearestArea")
    public Mono<MainInfo> getNearestAreaWeatherInfo(@RequestBody NearestAreaDTO nearestAreaDTO) {
        return observationalFitService.getNearestAreaWeatherInfo(nearestAreaDTO);
    }

    @ApiOperation(value = "현재 시간 조회")
    @GetMapping(value = "time")
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
