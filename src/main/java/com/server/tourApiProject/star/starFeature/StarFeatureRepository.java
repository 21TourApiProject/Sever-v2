package com.server.tourApiProject.star.starFeature;

import com.server.tourApiProject.hashTag.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * className : com.server.tourApiProject.star.starFeature
 * description : 설명
 * modification : 2023-07-06(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-07-06
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-07-06       jinhyeok       최초생성
 */
public interface StarFeatureRepository extends JpaRepository<StarFeature, Long> {
    //해시태그 이름으로 해시태그 id 찾기
    StarFeature findByStarFeatureName (@Param("starFeatureName") String starFeatureName);
}
