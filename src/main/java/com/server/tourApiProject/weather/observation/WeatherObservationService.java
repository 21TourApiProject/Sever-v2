package com.server.tourApiProject.weather.observation;

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
public class WeatherObservationService {

    private final WeatherObservationRepository weatherObservationRepository;

    public List<WeatherObservation> getAllObservation() {
        return weatherObservationRepository.findAll();
    }

    public Double getLightPollution(Double latitude, Double longitude) {
        return weatherObservationRepository.findByLatitudeAndLongitude(latitude, longitude).getLightPollution();
    }

    public List<WeatherObservation> get2Observation() {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        return weatherObservationRepository.findAllById(list);
    }

    public WeatherObservation getWeatherObservation(Long observationId) {
        return weatherObservationRepository.findById(observationId).get();
    }
}
