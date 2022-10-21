package com.server.tourApiProject.observation;

import com.server.tourApiProject.hashTag.HashTag;
import com.server.tourApiProject.hashTag.HashTagRepository;
import com.server.tourApiProject.myWish.MyWishParams01;
import com.server.tourApiProject.observation.observeImage.ObserveImage;
import com.server.tourApiProject.observation.observeImage.ObserveImageRepository;
import com.server.tourApiProject.search.SearchParams1;
import com.server.tourApiProject.observation.observeFee.ObserveFee;
import com.server.tourApiProject.observation.observeFee.ObserveFeeRepository;
import com.server.tourApiProject.observation.observeHashTag.ObserveHashTag;
import com.server.tourApiProject.observation.observeHashTag.ObserveHashTagParams;
import com.server.tourApiProject.observation.observeHashTag.ObserveHashTagRepository;
import com.server.tourApiProject.search.Filter;
import com.server.tourApiProject.touristPoint.touristData.TouristData;
import com.server.tourApiProject.touristPoint.touristDataHashTag.TouristDataHashTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
* @className : ObservationService.java
* @description : 관측지 서비스
* @modification : 2022-08-27 (gyul chyoung) 주석입력
* @author : gyul chyoung
* @date : 2022-08-27
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-27       gyul chyoung       주석최초생성
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ObservationService {
    private final ObservationRepository observationRepository;
    private final ObserveHashTagRepository observeHashTagRepository;
    private final HashTagRepository hashTagRepository;
    private final ObserveFeeRepository observeFeeRepository;
    private final ObserveImageRepository observeImageRepository;

    public List<Observation> getAllObservation() {
        return observationRepository.findAll();
    }

    /**
     * TODO 관측지 생성
     * @param  observationParams - 관측지 입력값
     * @return void
     */
    public void createObservation(ObservationParams observationParams) {
        Observation observation = new Observation();
        observation.setObservationName(observationParams.getObservationName());
        observation.setLink(observationParams.getLink());
        observation.setLatitude(observationParams.getLatitude());
        observation.setLongitude(observationParams.getLongitude());
        observation.setAddress(observationParams.getAddress());
        observation.setPhoneNumber(observationParams.getPhoneNumber());
        observation.setOperatingHour(observationParams.getOperatingHour());
        observation.setParking(observationParams.getParking());
        observation.setObserveType(observationParams.getObserveType());
        observation.setOutline(observationParams.getOutline());
        observationRepository.save(observation);
    }

    /**
     * TODO 관측지에 해당 해시태그 입력
     * @param  observationId - 관측지ID로 검색
     * @param observeHashTagParams - 관측지 해시태그 값
     * @return void
     * @throws
     */
    public void createObserveHashTags(Long observationId, List<ObserveHashTagParams> observeHashTagParams) {
        Observation observation = observationRepository.findById(observationId).orElseThrow(IllegalAccessError::new);
        for(ObserveHashTagParams p : observeHashTagParams) {
            ObserveHashTag observeHashTag = new ObserveHashTag();
            HashTag hashTag = hashTagRepository.findByHashTagName(p.getHashTagName());
            observeHashTag.setHashTagId(hashTag.getHashTagId());
            observeHashTag.setHashTagName(p.getHashTagName());
            observeHashTag.setObservation(observation);
            observeHashTag.setObservationId(observationId);
            observeHashTagRepository.save(observeHashTag);
        }

    }

    /**
     * TODO 관측지에 요금입력
     * @param  observationId - 관측지ID
     * @param  feeList - 관측지 요금 List
     * @return void
     * @throws
     */
    public void createObserveFees(Long observationId, List<ObserveFee> feeList) {
        Observation observation = observationRepository.findById(observationId).orElseThrow(IllegalAccessError::new);
        for (ObserveFee p : feeList) {
            observation.getObserveFees().add(p);
        }
    }

    /**
     * TODO 관측지ID로 관측지 조회
     * @param  observationId - 관측지ID
     * @return com.server.tourApiProject.observation.Observation
     * @throws
     */
    public Observation getObservation(Long observationId){
        Observation observation = observationRepository.findById(observationId).orElseThrow(IllegalAccessError::new);
        return observation;
    }
//test

    /**
     * TODO 필터와 검색어로 관측지 조회
     * @param  filter - 검색필터
     * @param  searchKey - 검색어
     * @return java.util.List<com.server.tourApiProject.search.SearchParams1> - 검색결과
     * @throws
     */
    public List<SearchParams1> getObservationWithFilter(Filter filter, String searchKey) {
        List<Long> areaCodeList = filter.getAreaCodeList();
        List<Long> hashTagIdList= filter.getHashTagIdList();    //필터 해쉬태그 리스트

        List<SearchParams1> resultParams = new ArrayList<>();   //최종결과 param 리스트
        List<Long> hashtagResult = new ArrayList<>();   //해쉬태그 결과
        List<Long> filterIdList = new ArrayList<>();    //필터결과(해쉬태그, 지도 포함)id 리스트
        List<Observation> searchResult = new ArrayList<>(); //필터+검색어 결과 리스트


        if (!hashTagIdList.isEmpty()) {
            for(Long hashTagId : hashTagIdList){
                List<ObserveHashTag> observeHashTags = observeHashTagRepository.findByHashTagId(hashTagId);
                for (ObserveHashTag observeHashTag : observeHashTags) {
                    Long observationId = observeHashTag.getObservationId();
                    if (!hashtagResult.contains(observationId)) { //관광지 중복 제거
                        hashtagResult.add(observationId);
                    }
                }
            }
        }

        if (!areaCodeList.isEmpty()) {
            for (Long areaCode : areaCodeList) {
                List<Observation> observationList = observationRepository.findByAreaCode(areaCode);
                if (hashTagIdList.isEmpty()) {
                    //해쉬태그 없으면 지역결과 전부추가
                    for (Observation observation : observationList) {
                        filterIdList.add(observation.getObservationId());
                    }
                } else {
                    //해쉬태그 있으면 필터 중첩
                    for (Observation observation : observationList) {
                        Long observationId = observation.getObservationId();
                        if (hashtagResult.contains(observationId)) {
                            //해시태그결과에서 지역 있으면 filter최종결과에 포함
                            filterIdList.add(observationId);
                        }
                    }
                }
            }
        } else {
            //area 비어있으면
            filterIdList = hashtagResult;
        }

        if (searchKey != null) {
            List<Observation> keyResult = new ArrayList<>();    //검색결과 받아올 리스트
            searchResult = observationRepository.findByObservationNameContainingOrOutlineContaining(searchKey, searchKey);
            keyResult = observationRepository.findByObservationNameContainingOrOutlineContaining(searchKey, searchKey);

            if (!hashTagIdList.isEmpty() || !areaCodeList.isEmpty()) {
                //필터 받은게 없으면 그냥 검색결과 전달, 있으면 중첩 검색
                for (Observation observation : keyResult) {
                    //전체 검색어 결과 돌면서
                    if (!filterIdList.contains(observation.getObservationId())) {
                        //필터결과에 검색어 결과 없으면 필터+검색어검색결과에서 삭제
                        searchResult.remove(observation);
                    }
                }
            }
        } else {
            for(Long p : filterIdList)
                searchResult.add(getObservation(p));
        }


        //결과 param에 넣음
        for(Observation observation : searchResult){

            if(observation.getObservationId()==999)
                continue;
            SearchParams1 searchParams1 = new SearchParams1();
            searchParams1.setItemId(observation.getObservationId());
            searchParams1.setTitle(observation.getObservationName());
            //주소를 두단어까지 줄임
            String address = observation.getAddress();
            int i = address.indexOf(' ');
            if (i != -1){
                int j = address.indexOf(' ', i+1);
                if(j != -1){
                    searchParams1.setAddress(observation.getAddress().substring(0, j));
                } else{
                    searchParams1.setAddress(observation.getAddress());
                }
            } else{
                searchParams1.setAddress(observation.getAddress());
            }
//            searchParams1.setAddress(observation.getAddress());
            searchParams1.setLatitude(observation.getLatitude());
            searchParams1.setLongitude(observation.getLongitude());
            searchParams1.setIntro(observation.getIntro());
            searchParams1.setContentType(observation.getObserveType());
            searchParams1.setLight(observation.getLight());
            if (!observeImageRepository.findByObservationId(observation.getObservationId()).isEmpty()) {
                ObserveImage observeImage = observeImageRepository.findByObservationId(observation.getObservationId()).get(0);
                searchParams1.setThumbnail(observeImage.getImage());
            } else {
                searchParams1.setThumbnail(null);
            }
            List<ObserveHashTag> hashTagList = observeHashTagRepository.findByObservationId(observation.getObservationId());
            List<String> hashTagNames = new ArrayList<>();
            int k = 0;
            for (ObserveHashTag hashTag : hashTagList){
                if(k>2)
                    break;
                hashTagNames.add(hashTag.getHashTagName());
                k++;
            }
            searchParams1.setHashTagNames(hashTagNames);

            resultParams.add(searchParams1);
        }
        return resultParams;
    }

}
