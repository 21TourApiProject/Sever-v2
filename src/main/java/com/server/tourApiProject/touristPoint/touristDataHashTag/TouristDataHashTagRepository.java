package com.server.tourApiProject.touristPoint.touristDataHashTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TouristDataHashTagRepository extends JpaRepository<TouristDataHashTag, Long> {

    List<TouristDataHashTag> findByContentId(@Param("contentId") Long contentId);

    @Query("select h.contentId from TouristDataHashTag h where h.hashTagId in :hashTagIds")
    List<Long> findByHashTagIds(@Param("hashTagIds") List<Long> hashTagIds);

}
