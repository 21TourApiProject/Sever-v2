package com.server.tourApiProject.myHashTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyHashTagRepository extends JpaRepository<MyHashTag, Long> {
    List<MyHashTag> findByUserId(@Param("userId") Long userId);
}
