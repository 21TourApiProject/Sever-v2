package com.server.tourApiProject.touristPoint.nearTouristData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NearTouristDataRepository extends JpaRepository<NearTouristData, Long>{
    List<NearTouristData> findByTouristDataId(@Param("touristDataId") Long touristDataId);
}
