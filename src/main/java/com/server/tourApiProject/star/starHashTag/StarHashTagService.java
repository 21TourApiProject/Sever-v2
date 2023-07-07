package com.server.tourApiProject.star.starHashTag;

import com.server.tourApiProject.hashTag.HashTag;
import com.server.tourApiProject.hashTag.HashTagRepository;
import com.server.tourApiProject.star.constellation.Constellation;
import com.server.tourApiProject.star.constellation.ConstellationRepository;
import com.server.tourApiProject.star.starFeature.StarFeature;
import com.server.tourApiProject.star.starFeature.StarFeatureRepository;
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
 * className : com.server.tourApiProject.star.starHashTag
 * description : 별자리 해시태그 Service
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
public class StarHashTagService {
    private final StarHashTagRepository starHashTagRepository;
    private final ConstellationRepository constellationRepository;
    private final StarFeatureRepository starFeatureRepository;

    /**
     * description: 별자리 id로 해시태그 가져오는 메소드.
     *
     * @param constId - the const id
     * @return the Starhashtag
     */
    public List<StarHashTag> getStarHashTag(Long constId) {
        return starHashTagRepository.findByConstId(constId);
    }

    /**
     * description: 해시태그 클래스에서 해시태그 이름 리스트를 가져오는 메소드.
     *
     * @param constId - the const id
     * @return the star hash tag name
     */
    public List<String> getStarHashTagName(Long constId) {
        List<String> starHashTagNameList =new ArrayList<>();
        List<StarHashTag> starHashTagList = starHashTagRepository.findByConstId(constId);
        for(StarHashTag p : starHashTagList) {
            starHashTagNameList.add(p.getHashTagName());
        }
        return starHashTagNameList;
    }
    /**
     * description: 별자리 해시태그 생성하는 메소드.
     *
     * @param constId            - the const id
     * @param starHashTagParams - the star hash tag params
     */
    public void createStarHashTags(Long constId,List<StarHashTagParams> starHashTagParams) {
        for (StarHashTagParams p : starHashTagParams) {
            Constellation constellation = constellationRepository.findById(constId).orElseThrow(IllegalAccessError::new);
            StarHashTag starHashTag = new StarHashTag();
            starHashTag.setHashTagName(p.getHashTagName());
            starHashTag.setConstellation(constellation);
            starHashTag.setConstId(constellation.getConstId());
            StarFeature starFeature = starFeatureRepository.findByStarFeatureName(p.getHashTagName());
            starHashTag.setHashTagId(starFeature.getStarFeatureId());
            starHashTagRepository.save(starHashTag);
        }
    }

    /**
     * description:별자리 해시태그 삭제하는 메소드.
     */
    public void deleteStarHashTags(){starHashTagRepository.deleteAll();}
}
