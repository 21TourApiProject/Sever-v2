package com.server.tourApiProject.observation.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * 쿼리명: findByObservationId
     * 설명: 관측지 ID로 관측지 코스 조회
    * parameter type = Long
     * result type = List<Course>
     */
    List<Course> findByObservationId(@Param("observationId") Long observationId);
}
