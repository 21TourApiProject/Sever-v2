package com.server.tourApiProject.observation.observeHashTag;

import com.server.tourApiProject.touristPoint.touristDataHashTag.TouristDataHashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObserveHashTagRepository extends JpaRepository<ObserveHashTag, Long> {
    /**
     * 쿼리명: findByObservationId
     * 설명: 관측지ID로 해당되는 해쉬태그 리스트 조회
     * parameter type = Long
     * result type = List<ObserveHashTag>
     */
    List<ObserveHashTag> findByObservationId(@Param("observationId") Long observationId);

    /**
     * 쿼리명: findByHashTagId
     * 설명: 해쉬태그 ID로 해당되는 관측지 해쉬태그 조회(해쉬태그에 해당되는 관측지ID 조회)
     * parameter type = Long
     * result type = List<ObserveHashTag>
     */
    List<ObserveHashTag> findByHashTagId(@Param("hashTagId") Long hashTagId);
}
