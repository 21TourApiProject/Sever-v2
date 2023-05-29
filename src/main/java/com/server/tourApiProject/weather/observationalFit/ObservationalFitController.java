package com.server.tourApiProject.weather.observationalFit;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"7.3 날씨 - 관측적합도"})
@RestController
@RequestMapping(value = "weather/v2")
@RequiredArgsConstructor
public class ObservationalFitController {

    private final ObservationalFitService observationalFitService;

    /**
     * 하루의 시간별 관측적합도(익일 18-23, 명일 0-6)를 조회하여 db에 저장
     *
     * @param date 날짜
     */
    @PostMapping("/observationalFit/save/{date}")
    public void setObservationFit(@PathVariable String date) {
        observationalFitService.setObservationFit(date); // 2023-05-29
    }


}
