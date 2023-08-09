package com.server.tourApiProject.weather.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
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

    private final static Map<String, String> SD = new HashMap<>() {{
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

    public Long getAreaIdByAddress(String address) {
        String[] split = address.split(" ");
        String city = SD.getOrDefault(split[0], "");

        if (split.length == 2)
            return weatherAreaRepository.findBySDAndEMD1(city, split[1]).getAreaId(); // 세종특별자치시 소정면
        if (split.length == 3)
            return weatherAreaRepository.findBySDAndEMD2(city, split[2]).getAreaId(); // 서울특별시 서대문구 북가좌동
        if (split.length == 4)
            return weatherAreaRepository.findBySDAndEMD3(city, split[3]).getAreaId(); // 경기도 고양시 일산서구 일산동
        else return -1L;
    }

    public Map<String, String> getNearestArea(NearestDTO nearestDTO) {
        double minDistance = Double.MAX_VALUE;
        String EMD = "";
        List<WeatherArea> list;

        if (nearestDTO.getSgg().equals("세종")) {
            list = weatherAreaRepository.findBySD("세종");
        } else {
            list = weatherAreaRepository.findByEMD1(nearestDTO.getSgg()).orElseGet(() -> weatherAreaRepository.findByEMD2(nearestDTO.getSgg()).get());
        }
        for (WeatherArea area : list) {
            double calculate = Math.pow(Math.abs(nearestDTO.getLatitude() - area.getLatitude()), 2) + Math.pow(Math.abs(nearestDTO.getLongitude() - area.getLongitude()), 2);
            if (calculate < minDistance) {
                minDistance = calculate;
                if (nearestDTO.getSgg().equals("세종")) {
                    EMD = area.getEMD1();
                } else {
                    EMD = area.getEMD2();
                    if (area.getEMD3() != null) EMD += " " + area.getEMD3();
                }
                System.out.println("EMD = " + EMD);
            }
        }
        return Map.of("EMD", EMD);
    }
}
