package com.server.tourApiProject.touristPoint.touristData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TouristDataRepository extends JpaRepository<TouristData, Long> {
    TouristData findByContentId(@Param("contentId") Long contentId);

    List<TouristData> findByContentTypeId(@Param("contentTypeId") Long contentTypeId);

    List<TouristData> findByIsJu(@Param("isJu") Integer isJu);

    @Query(value = "select h from TouristData h where h.title like %:title% and h.areaCode in :areaCodes")
    List<TouristData> findByAreaCodesTitle(@Param("title") String title, @Param("areaCodes") List<Long> areaCodes);

    List<TouristData> findByTitleContaining(@Param("title") String title);

    @Query(value = "select DISTINCT h from TouristData h join fetch h.touristDataHashTags")
    List<TouristData> findAllJoinFetch();

    List<TouristData> findByFirstImage(@Param("firstImage") String firstImage);

}
