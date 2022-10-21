package com.server.tourApiProject.touristPoint.touristDataHashTag;

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
 * @className : TouristDataHashTagService.java
 * @description : TouristDataHashTag Service 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class TouristDataHashTagService {

    private final TouristDataHashTagRepository touristDataHashTagRepository;

    public List<String> getTouristDataHashTag(Long contentId) {
        /**
         * description: 관광지 해시태그 정보 조회
         *
         * @param contentId - 컨텐츠 id
         * @return 해시태그 list
         */
        List<TouristDataHashTag> list = touristDataHashTagRepository.findByContentId(contentId);
        List<String> result = new ArrayList<>();
        for(TouristDataHashTag dataHashTag : list){
            result.add(dataHashTag.getHashTagName());
        }
        return result;
    }
}
