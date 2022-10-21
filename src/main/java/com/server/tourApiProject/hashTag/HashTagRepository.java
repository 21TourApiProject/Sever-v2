package com.server.tourApiProject.hashTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    //해시태그 이름으로 해시태그 id 찾기
    HashTag findByHashTagName(@Param("hashTagName") String hashTagName);
}
