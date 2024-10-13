package com.server.tourApiProject.weather.observationalFit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
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
    @Scheduled(cron = "${scheduler.observationalFit.save.time}")
    public void saveHourObservationalFit() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDate.now().format(formatter);
        log.info("Start Save ObservationalFit Scheduler | {}", today);
        observationalFitService.setObservationFit(today);
    }

    /**
     * 실행 주기 : 매달 1일 8시
     * JOB : 불필요한 시간별 관측적합도 삭제
     */
    @Scheduled(cron = "${scheduler.observationalFit.delete.time}")
    public void deleteHourObservationalFit() {
        DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate today = LocalDate.now();
        YearMonth lastMonth = YearMonth.from(today).minusMonths(2);
        int lengthOfMonth = lastMonth.lengthOfMonth();

        log.info("Start Delete ObservationalFit Scheduler | {}", today);
        observationalFitService.deleteObservationFit(lastMonth.format(formatterMonth), lengthOfMonth);
    }

}