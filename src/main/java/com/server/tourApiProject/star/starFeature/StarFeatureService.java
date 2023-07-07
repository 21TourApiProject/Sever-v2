package com.server.tourApiProject.star.starFeature;

import com.server.tourApiProject.hashTag.HashTag;
import com.server.tourApiProject.touristPoint.area.AreaFilterParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
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
public class StarFeatureService {

    private final StarFeatureRepository starFeatureRepository;

    /**
     * description: 모든 별자리 특성 조회
     *
     * @return HashTag list
     */
    public List<StarFeature> getAllStarFeature() {
        return starFeatureRepository.findAll();
    }

    /**
     * description: 새로운 해시태그 작성
     *
     * @param starFeature - Star Entity
     */
    public void createStarFeature(StarFeature starFeature) {
        starFeatureRepository.save(starFeature);
    }

}
