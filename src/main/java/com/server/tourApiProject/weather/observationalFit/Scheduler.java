package com.server.tourApiProject.weather.observationalFit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ObservationalFitService observationalFitService;

    /**
     * 실행 주기 : 매일 7시 0분 0초
     * JOB : 시간별 관측적합도 정보 호출
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void saveHourObservationalFit() {
//        observationFitService.setObservationFit(date);
    }

}