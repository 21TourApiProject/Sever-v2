package com.server.tourApiProject.observation.observeHashTag;

import com.server.tourApiProject.hashTag.HashTagRepository;
import com.server.tourApiProject.observation.ObservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
* @className : ObserveHashTagService.java
* @description : 관측지 해쉬태그 서비스
* @modification : 2022-08-29 (gyul chyoung) 주석추가
* @author : gyul chyoung
* @date : 2022-08-29
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-29       gyul chyoung       주석추가가
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ObserveHashTagService {
    private final ObserveHashTagRepository observeHashTagRepository;

    /**
     * TODO 관측지 ID로 관측지에 해당하는 해쉬태그 조회
     * @param  observationId -
     * @return java.util.List<java.lang.String>
     * @throws
     */
    public List<String> getObserveHashTag(Long observationId) {
        List<String> observeHashTagNameList = new ArrayList<>();
        List<ObserveHashTag> observeHashTagList = observeHashTagRepository.findByObservationId(observationId);
        for(ObserveHashTag p : observeHashTagList) {
            observeHashTagNameList.add(p.getHashTagName());
        }
        return observeHashTagNameList;
    }

}
