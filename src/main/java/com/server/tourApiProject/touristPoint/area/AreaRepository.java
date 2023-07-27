package com.server.tourApiProject.touristPoint.area;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {

    @Query("select distinct a.areaCode as id, a.areaName as name from Area a")
    List<AreaFilterParams.AreaFilter> findFilterArea();
    Area findByAreaId(@Param("areaId")Long areaId);
}
