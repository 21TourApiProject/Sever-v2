package com.server.tourApiProject.weather.common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CommonService {

    // DB 에 저장된 ObservationalFit 을 조회하기 위한 기준 날짜
    public static String getObservationalFitDBBaseDate() {
        int hour = Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));
        if (hour < 7) {
            return LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}
