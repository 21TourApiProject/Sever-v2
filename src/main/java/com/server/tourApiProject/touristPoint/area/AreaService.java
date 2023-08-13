package com.server.tourApiProject.touristPoint.area;

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
 * @className : AreaService.java
 * @description : Area Service 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class AreaService {

    private final AreaRepository areaRepository;

    /**
     * description: 지역의 시군구 정보 입력
     *
     * @param areaParams - Are param
     */
    public void createArea(AreaParams areaParams) {
        Area area = new Area();
        area.setAreaCode(areaParams.getCode1());
        area.setAreaName(areaParams.getName1());
        area.setSigunguCode(areaParams.getCode2());
        area.setSigunguName(areaParams.getName2());
        areaRepository.save(area);
    }

    public List<AreaFilterParams> findAreaFilter(){
        List<AreaFilterParams.AreaFilter> filterArea = areaRepository.findFilterArea();

        List<AreaFilterParams> result = new ArrayList<>();
        for (AreaFilterParams.AreaFilter areaFilter : filterArea) {
            result.add(new AreaFilterParams(areaFilter.getId(), areaFilter.getName(), "AREA"));
        }

        return result;
    }

}
