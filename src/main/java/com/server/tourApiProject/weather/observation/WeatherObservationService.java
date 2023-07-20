package com.server.tourApiProject.weather.observation;

import com.server.tourApiProject.weather.area.WeatherArea;
import com.server.tourApiProject.weather.area.WeatherAreaRepository;
import com.server.tourApiProject.weather.area.WeatherLocationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WeatherObservationService {

    private final WeatherObservationRepository weatherObservationRepository;
    private final WeatherAreaRepository weatherAreaRepository;

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

    public List<WeatherLocationDTO> getWeatherLocations() {
        List<WeatherLocationDTO> result = new ArrayList<>();

        for (WeatherObservation observation : weatherObservationRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))) {
            result.add(new WeatherLocationDTO(observation.getName(), observation.getSearchAddress(), null, observation.getObservationId(), observation.getLatitude(), observation.getLongitude()));
        }
        for (WeatherArea area : weatherAreaRepository.findAll()) {
            result.add(new WeatherLocationDTO(area.getEMD(), area.getSGG() + " " + area.getSigungu(), area.getAreaId(), null, area.getLatitude(), area.getLongitude()));
        }
        return result;
    }

}
