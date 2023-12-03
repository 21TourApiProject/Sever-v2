package com.server.tourApiProject.weather.observationalFit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObservationalFitRepository extends JpaRepository<ObservationalFit, Long>, ObservationalFitRepositoryCustom{

    // 날짜로 시간별 관측적합도 조회
    List<ObservationalFit> findByDate(String date);
    ObservationalFit findByDateAndObservationCode(String date, Long observationCode);

}
