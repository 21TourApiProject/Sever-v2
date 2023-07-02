package com.server.tourApiProject.observation;

import com.server.tourApiProject.observation.observeHashTag.ObserveHashTag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
* @className : ObservationSpecification.java
* @description : 동적 쿼리 생성을 위하여 만든 Specification 클래스
* @modification : 2022-11-03 (gyul chyoung) methodA수정
* @author : gyul chyoung
* @date : 2022-11-03
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-11-03       gyul chyoung       최초생성
 */
public class ObservationSpecification {

    /**
     * TODO  검색어 존재시 관측지이름, 개요에서 검색어 검색
     * @param  searchKey - 검색어
     * @return org.springframework.data.jpa.domain.Specification<com.server.tourApiProject.observation.Observation>
     * @throws
     */
    public static Specification<Observation> likeSearchKey(String searchKey) {


        return new Specification<Observation>() {
            @Override
            public Predicate toPredicate(Root<Observation>
                                                 root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicateForObservationName = criteriaBuilder
                        .like(root.get("observationName"), "%" + searchKey + "%");  //관측지 이름에서 검색어 like 조회
                Predicate predicateForOutline = criteriaBuilder
                        .like(root.get("outline"), "%" + searchKey + "%");  //관측지 개요에서 검색어 like 조회

                return criteriaBuilder.and(predicateForObservationName, predicateForOutline);   //and로 조건 두개 병합합
            }
        };
    }

    /**
     * TODO  hashtagId로 해당 해쉬태그 포함 관측지 조회 조건
     * @param  hashtagIds - 해쉬태그 Id 리스트
     * @return org.springframework.data.jpa.domain.Specification<com.server.tourApiProject.observation.Observation>
     * @throws
     */
    public static Specification<Observation> existHashtagId(List<Long> hashtagIds) {

        return new Specification<Observation>() {
            @Override
            public Predicate toPredicate(Root<Observation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Subquery<ObserveHashTag> hashTagSubquery = query.subquery(ObserveHashTag.class);
                Root<ObserveHashTag> observeHashTag = hashTagSubquery.from(ObserveHashTag.class);
                hashTagSubquery.select(observeHashTag).where(criteriaBuilder
                        .equal(observeHashTag.get("observationId"), root.get("observationId")), //ObservehashTag테이블과 observation테이블 조인조외
                        criteriaBuilder.and(observeHashTag.get("hashTagId").in(hashtagIds))); // 인자로 받은 해쉬태그리스트에 포함되는지 확인

                return criteriaBuilder.exists(hashTagSubquery);
            }
        };
    }

    /**
     * TODO AreaCode in 조회
     * @param  areaCodes - 지역코드 id 리스트
     * @return org.springframework.data.jpa.domain.Specification<com.server.tourApiProject.observation.Observation>
     * @throws
     */
    public static Specification<Observation> inAreaCodes(List<Long> areaCodes) {

        return new Specification<Observation>() {
            @Override
            public Predicate toPredicate(Root<Observation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(root.get("areaCode").in(areaCodes));
            }
        };

    }

    public static Specification<Observation> likeSearchKeyAndInFilter(String searchKey, List<Long> hashtagIds, List<Long> areaCodes) {
        return new Specification<Observation>() {
            @Override
            public Predicate toPredicate(Root<Observation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (searchKey!=null && !searchKey.isEmpty()) {
                    Predicate predicateForObservationName = criteriaBuilder
                            .like(root.get("observationName"), "%" + searchKey + "%");  //관측지 이름에서 검색어 like 조회
                    Predicate predicateForOutline = criteriaBuilder
                            .like(root.get("outline"), "%" + searchKey + "%");  //관측지 개요에서 검색어 like 조회

                    predicates.add(criteriaBuilder.or(predicateForObservationName, predicateForOutline));   //and로 조건 두개 병합합
                }
                if (hashtagIds!=null && !hashtagIds.isEmpty()) {
                    Subquery<ObserveHashTag> hashTagSubquery = query.subquery(ObserveHashTag.class);
                    Root<ObserveHashTag> observeHashTag = hashTagSubquery.from(ObserveHashTag.class);
                    hashTagSubquery.select(observeHashTag).where(criteriaBuilder
                                    .equal(observeHashTag.get("observationId"), root.get("observationId")), //ObservehashTag테이블과 observation테이블 조인조외
                            criteriaBuilder.and(observeHashTag.get("hashTagId").in(hashtagIds))); // 인자로 받은 해쉬태그리스트에 포함되는지 확인

                    predicates.add(criteriaBuilder.exists(hashTagSubquery));
                }
                if (areaCodes!=null && !areaCodes.isEmpty()) {
                    predicates.add(criteriaBuilder.and(root.get("areaCode").in(areaCodes)));
                }

                final Predicate[] predicateArray = new Predicate[predicates.size()];
                return criteriaBuilder.and(predicates.toArray(predicateArray));
            }
        };
    }
}
