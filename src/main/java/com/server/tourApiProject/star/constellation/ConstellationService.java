package com.server.tourApiProject.star.constellation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

/**
* @className : ConstellationService.java
* @description : 별자리 service 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class ConstellationService {
    private final ConstellationRepository constellationRepository;

    /**
     * TODO 별자리 생성
     * @param  constellation - 별자리 정보
     * @return void
     */
    public void createConstellation(Constellation constellation) {
        constellationRepository.save(constellation);
    }

    /**
     * TODO 별자리 조회
     * @return List<ConstellationParams>
     */
    public List<ConstellationParams> getConstellation() {
        List<ConstellationParams> result = new ArrayList<>();
        List<Constellation> list = constellationRepository.findAll();

        return getConstellationParams(result, list);
    }

  /**
       * TODO 별자리 이름 조회
       * @return List<ConstellationParams2>
   */
    public List<ConstellationParams2> getConstNames() {
        List<ConstellationParams2> result = new ArrayList<>();
        List<Constellation> list = constellationRepository.findAll();

        return getConstellationParams2(result, list);
    }

    /**
     * TODO 별자리 id로 별자리 id, 이름, 영어 이름 조회
     * @param result - 별자리 param list
     * @param list - 별자리 list
     * @return Constellation
     */
    private List<ConstellationParams> getConstellationParams(List<ConstellationParams> result, List<Constellation> list) {
        for (Constellation cl : list) {
            Constellation constellation = constellationRepository.findById(cl.getConstId()).orElseThrow(IllegalAccessError::new);

            ConstellationParams params = new ConstellationParams();
            params.setConstId(constellation.getConstId());
            params.setConstName(constellation.getConstName());
            params.setConstEng(constellation.getConstEng());
            result.add(params);
        }
        return result;
    }

    /**
     * TODO 별자리 id로 별자리 이름 조회
     * @param result - 별자리 이름 list
     * @param list - 별자리 list
     * @return Constellation
     */
    public List<ConstellationParams2> getConstellationParams2(List<ConstellationParams2> result, List<Constellation> list) {
        for (Constellation cl : list) {
            Constellation constellation = constellationRepository.findById(cl.getConstId()).orElseThrow(IllegalAccessError::new);

            ConstellationParams2 params2 = new ConstellationParams2();
            params2.setConstName(constellation.getConstName());
            result.add(params2);
        }
        return result;
    }

    /**
     * TODO 오늘 볼 수 있는 별자리 정보 조회
     * @return List<ConstellationParams>
     */
    public List<ConstellationParams> getTodayConst() {
        List<ConstellationParams> result = new ArrayList<>();
        List<Constellation> resultAll = new ArrayList<>();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));

        List<Constellation> list1 = constellationRepository.findByStartDate1LessThanEqualAndEndDate1GreaterThanEqual(currentDate, currentDate);
        List<Constellation> list2 = constellationRepository.findByStartDate2LessThanEqualAndEndDate2GreaterThanEqual(currentDate, currentDate);

        resultAll.addAll(list1);
        resultAll.addAll(list2);

        return getConstellationParams(result, resultAll);
    }

    /**
     * TODO 오늘 볼 수 있는 별자리 이름 조회
     * @return List<ConstellationParams2>
     */
    public List<ConstellationParams2> getTodayConstName() {
        List<ConstellationParams2> result = new ArrayList<>();
        List<Constellation> resultAll = new ArrayList<>();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));

        List<Constellation> list1 = constellationRepository.findByStartDate1LessThanEqualAndEndDate1GreaterThanEqual(currentDate, currentDate);
        List<Constellation> list2 = constellationRepository.findByStartDate2LessThanEqualAndEndDate2GreaterThanEqual(currentDate, currentDate);

        resultAll.addAll(list1);
        resultAll.addAll(list2);

        return getConstellationParams2(result, resultAll);
    }

    /**
     * TODO 별자리 이름으로 별자리 정보 조회
     * @param constName - 별자리 이름
     * @return Constellation
     */
    public Constellation getDetailConst(String constName) {
        Constellation constellation = constellationRepository.findByConstName(constName);
        return constellation;
    }
}

