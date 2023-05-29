package com.server.tourApiProject.weather.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

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
}
