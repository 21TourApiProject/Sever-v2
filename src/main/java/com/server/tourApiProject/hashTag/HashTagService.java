package com.server.tourApiProject.hashTag;

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
 * @className : HashTagService.java
 * @description : HashTag service 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class HashTagService {
    private final HashTagRepository hashTagRepository;

    /**
     * description: 모든 해시태그 조회
     *
     * @return HashTag list
     */
    public List<HashTag> getAllHashTag() {
        return hashTagRepository.findAll();
    }

    /**
     * description: 새로운 해시태그 작성
     *
     * @param hashTag - HashTag Entity
     */
    public void createHashTag(HashTag hashTag) {
        hashTagRepository.save(hashTag);
    }

    public List<AreaFilterParams> getFilterHashTag() {
        List<HashTag> hashTagList = hashTagRepository.findAll();
        List<AreaFilterParams> result = new ArrayList<>();

        for (HashTag h : hashTagList) {
            result.add(new AreaFilterParams(h.getHashTagId(), h.getHashTagName(), h.getCategory().toString()));
        }

        return result;
    }

}
