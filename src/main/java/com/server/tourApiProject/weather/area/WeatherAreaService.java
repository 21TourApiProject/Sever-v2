package com.server.tourApiProject.weather.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WeatherAreaService {

    private final WeatherAreaRepository weatherAreaRepository;

    public Double getLightPollution(Double latitude, Double longitude) {
        return weatherAreaRepository.findByLatitudeAndLongitude(latitude, longitude).getLightPollution();
    }
}
