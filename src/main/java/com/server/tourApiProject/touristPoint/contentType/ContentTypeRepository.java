package com.server.tourApiProject.touristPoint.contentType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
    ContentType findByCat3Code(@Param("cat3Code") String cat3Code);
}
