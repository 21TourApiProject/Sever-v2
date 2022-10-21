package com.server.tourApiProject.observation.observeImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObserveImageRepository extends JpaRepository <ObserveImage,Long>{
    /**
     * 쿼리명: findByObservationId
     * 설명: 관측지 ID로 이미지조회
     * parameter type = Long
     * result type = List<ObserveImage>
     */
    List<ObserveImage> findByObservationId(@Param("observationId") Long observationId);
}
