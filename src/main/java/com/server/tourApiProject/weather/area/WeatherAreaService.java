package com.server.tourApiProject.weather.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WeatherAreaService {

    private final WeatherAreaRepository weatherAreaRepository;

    public Double getLightPollution(Long areaId) {
        return weatherAreaRepository.findById(areaId).get().getLightPollution();
    }

    public WeatherArea getWeatherArea(Long areaId) {
        return weatherAreaRepository.findById(areaId).get();
    }

    private final static Map<String, String> SGG = new HashMap<>() {{
        put("서울특별시", "서울");
        put("경기도", "경기");
        put("인쳔광역시", "인천");
        put("강원도", "강원");
        put("충청북도", "충북");
        put("충청남도", "충남");
        put("대전광역시", "대전");
        put("세종특별자치시", "세종");
        put("광주광역시", "광주");
        put("전라북도", "전북");
        put("전라남도", "전남");
        put("대구광역시", "대구");
        put("경상북도", "경북");
        put("경상남도", "경남");
        put("부산광역시", "부산");
        put("울산광역시", "울산");
        put("제주특별자치도", "제주");
    }};

    public Long getAreaIdByAddress(String address) { // 서울특별시 서대문구 북가좌동
        String[] split = address.split(" ");
        String city = SGG.getOrDefault(split[0], "");
        WeatherArea weatherArea = weatherAreaRepository.findBySGGAndEMD(city, split[2]);
        return weatherArea.getAreaId();
    }
}
