package com.server.tourApiProject.weather.WtArea;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

/**
* @className : WtAreaService.java
* @description : 날씨 service 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class WtAreaService {
    private final WtAreaRepository wtAreaRepository;

    /**
     * TODO 날씨 정보 조회
     * @param cityName - 시 이름
     * @param provName - 구 이름
     * @return WtAreaParams
     */
    public WtAreaParams getAreaInfo(String cityName, String provName) {
        WtArea wtArea = wtAreaRepository.findByCityNameAndProvName(cityName, provName);

        if (wtArea.getCityName().equals(cityName) && wtArea.getProvName().equals(provName)) {
            WtAreaParams wtAreaParams = new WtAreaParams();
            wtAreaParams.setLatitude(wtArea.getLatitude());
            wtAreaParams.setLongitude(wtArea.getLongitude());
            wtAreaParams.setMinLightPol(wtArea.getMinLightPol());
            wtAreaParams.setMaxLightPol(wtArea.getMaxLightPol());
            return wtAreaParams;
        }
        return WtAreaParams.builder().build();
    }
}
