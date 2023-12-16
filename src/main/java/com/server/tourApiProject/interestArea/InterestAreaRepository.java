package com.server.tourApiProject.interestArea;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestAreaRepository extends JpaRepository<InterestArea, Long> {

    List<InterestArea> findByUserId(Long userId);
    Optional<InterestArea> findByUserIdAndRegionIdAndRegionType(Long userId, Long regionId, Integer regionType);
}
