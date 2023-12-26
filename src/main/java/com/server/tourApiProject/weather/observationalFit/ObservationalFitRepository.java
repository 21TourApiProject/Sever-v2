package com.server.tourApiProject.weather.observationalFit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObservationalFitRepository extends JpaRepository<ObservationalFit, Long>, ObservationalFitRepositoryCustom{

    // 날짜로 시간별 관측적합도 조회
    List<ObservationalFit> findByDate(String date);

    // 날짜, 관측지 id 로 시간별 관측적합도 조회
    Optional<ObservationalFit> findByDateAndObservationCode(String date, Long observationCode);

}
