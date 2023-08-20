package com.server.tourApiProject.weather.observationalFit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ObservationalFitService observationalFitService;

    /**
     * 실행 주기 : 매일 7시 (18시 이전에만 하면 됩)
     * JOB : 시간별 관측적합도 정보 호출
     */
    @Scheduled(cron = "0 0 7 * * *")
    public void saveHourObservationalFit() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDate.now().format(formatter);
        log.info("Start ObservationalFit Schedule | {}", today);
        observationalFitService.setObservationFit(today);
    }

}