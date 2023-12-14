package com.server.tourApiProject.observation;

import com.server.tourApiProject.observation.model.ObservationParams;
import com.server.tourApiProject.observation.model.ObservationSimpleParams;
import com.server.tourApiProject.observation.observeFee.ObserveFee;
import com.server.tourApiProject.observation.observeHashTag.ObserveHashTagParams;
import com.server.tourApiProject.search.Filter;
import com.server.tourApiProject.search.SearchParams1;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ObservationService {

    public List<Observation> getAllObservation();

    /**
     * TODO 관측지 생성
     *
     * @param observationParams - 관측지 입력값
     * @return void
     */
    public void createObservation(ObservationParams observationParams);

    /**
     * TODO 관측지에 해당 해시태그 입력
     *
     * @param observationId        - 관측지ID로 검색
     * @param observeHashTagParams - 관측지 해시태그 값
     * @return void
     * @throws
     */
    public void createObserveHashTags(Long observationId, List<ObserveHashTagParams> observeHashTagParams);

    /**
     * TODO 관측지에 요금입력
     *
     * @param observationId - 관측지ID
     * @param feeList       - 관측지 요금 List
     * @return void
     * @throws
     */
    public void createObserveFees(Long observationId, List<ObserveFee> feeList);

    /**
     * TODO 관측지ID로 관측지 조회
     *
     * @param observationId - 관측지ID
     * @return com.server.tourApiProject.observation.Observation
     * @throws
     */
    public Observation getObservation(Long observationId);

    /**
     * TODO 필터와 검색어로 관측지 조회
     *
     * @param filter    - 검색필터
     * @param searchKey - 검색어
     * @return java.util.List<com.server.tourApiProject.search.SearchParams1> - 검색결과
     * @throws
     */
    public List<SearchParams1> getObservationWithFilter(Filter filter, String searchKey, Pageable pageable);

    /**
     * TODO 필터와 검색어로 관측지 개수 조회
     *
     * @param filter    - 검색필터
     * @param searchKey - 검색어
     * @return Long - 검색결과의 개수
     * @throws
     */
    public Long getCountWithFilter(Filter filter, String searchKey);

    /**
     * TODO 관측지ID리스트로 관측지 간단정보 조회
     *
     * @param observationIds - 관측지ID 리스트
     * @return com.server.tourApiProject.observation.Observation
     * @throws
     */
    public List<ObservationSimpleParams> getObservationSimpleList(List<Long> observationIds);

    /**
     * TODO 관측지 관측적합도 높은 순으로 10개 조회
     *
     * @param
     * @return com.server.tourApiProject.observation.Observation
     * @throws
     */
    public List<ObservationSimpleParams> getBestFitObservationList();

    /**
     * TODO 관측지 가까운 순으로 3개 조회
     *
     * @param
     * @return com.server.tourApiProject.observation.Observation
     * @throws
     */
    public List<ObservationSimpleParams> getNearObservationIds(Long areaId, int size);
}

