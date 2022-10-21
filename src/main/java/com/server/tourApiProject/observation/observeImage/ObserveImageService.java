package com.server.tourApiProject.observation.observeImage;

import com.server.tourApiProject.observation.ObservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
* @className : ObserveImageService.java
* @description : 관측지 이미지 서비스
* @modification : 2022-08-29 (gyul chyoung) 주석추가
* @author : gyul chyoung
* @date : 2022-08-29
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-29       gyul chyoung       주석추가
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ObserveImageService {
    private final ObserveImageRepository observeImageRepository;

    /**
     * TODO 관측지 이미지 주소 조회
     * @param  observationId
     * @return java.util.List<java.lang.String>
     * @throws
     */
    public List<String> getObserveImage(Long observationId) {

        List<String> observeImagePathList =new ArrayList<>();
        List<ObserveImage> observeImageList = observeImageRepository.findByObservationId(observationId);
        for(ObserveImage p : observeImageList) {
            observeImagePathList.add(p.getImage());
        }
        return observeImagePathList;
    }

    /**
     * TODO 관측지 이미지, 출처 조회
     * @param  observationId
     * @return java.util.List<com.server.tourApiProject.observation.observeImage.ObserveImageParams2>
     * @throws
     */
    public List<ObserveImageParams2> getObserveImageInfo(Long observationId) {
        List<ObserveImageParams2> observeImageInfos =new ArrayList<>();
        List<ObserveImage> observeImageList = observeImageRepository.findByObservationId(observationId);
        for(ObserveImage p : observeImageList) {
            ObserveImageParams2 info = new ObserveImageParams2();
            info.setImage(p.getImage());
            info.setImageSource(p.getImageSource());
            observeImageInfos.add(info);
        }
        return observeImageInfos;
    }



}
