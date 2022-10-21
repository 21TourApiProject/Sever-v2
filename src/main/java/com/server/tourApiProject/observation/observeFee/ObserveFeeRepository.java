package com.server.tourApiProject.observation.observeFee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObserveFeeRepository extends JpaRepository<ObserveFee, Long> {
    /**
     * 쿼리명: findByObservationId
     * 설명: 관측지 ID로 관측지 요금 조회
     * parameter type = Long
     * result type = List<ObserveFee>
     */
    List<ObserveFee> findByObservationId(@Param("observationId") Long observationId);
}
