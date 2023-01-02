package com.server.tourApiProject.star.starHashTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * className : com.server.tourApiProject.star.starHashTag
 * description : 설명
 * modification : 2022-12-27(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2022-12-27
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2022-12-27       jinhyeok       최초생성
 */
public interface StarHashTagRepository extends JpaRepository<StarHashTag,Long> {
    List<StarHashTag> findByConstId(@Param("constId") Long constId);
    List<StarHashTag> findByHashTagId(@Param("hashTagId")Long hashTagId);
}
