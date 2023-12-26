package com.server.tourApiProject.weather.observation;

import com.server.tourApiProject.weather.area.WeatherArea;
import com.server.tourApiProject.weather.area.WeatherAreaRepository;
import com.server.tourApiProject.weather.area.WeatherLocationDTO;
import com.server.tourApiProject.weather.observationalFit.ObservationalFit;
import com.server.tourApiProject.weather.observationalFit.ObservationalFitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.server.tourApiProject.weather.common.CommonService.getObservationalFitDBBaseDate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WeatherObservationService {


    private final WeatherObservationRepository weatherObservationRepository;
    private final ObservationalFitRepository observationalFitRepository;
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

        List<ObservationalFit> observationalFitList = observationalFitRepository.findByDate(getObservationalFitDBBaseDate());

        for (WeatherObservation observation : weatherObservationRepository.findAll()) {
            boolean findFit = false;

            for (ObservationalFit observationalFit : observationalFitList) {
                if (Objects.equals(observationalFit.getObservationCode(), observation.getObservationId())) {
                    findFit = true;
                    result.add(new WeatherLocationDTO(observation.getName(), observation.getSearchAddress(), null, observation.getObservationId(),
                            observation.getLatitude(), observation.getLongitude(), Math.round(observationalFit.getBestObservationalFit())));
                    break;
                }
            }

            if (!findFit) {
                result.add(new WeatherLocationDTO(observation.getName(), observation.getSearchAddress(), null, observation.getObservationId(),
                        observation.getLatitude(), observation.getLongitude(), null));
            }
        }

        result = result.stream()
                .sorted(Comparator.comparing(WeatherLocationDTO::getObservationalFit, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());

        for (WeatherArea area : weatherAreaRepository.findAll()) {
            String title = area.getEMD1();
            String subtitle = "";

            if (area.getEMD3() != null) {
                title = area.getEMD3();
                subtitle = area.getSD() + " " + area.getEMD1() + " " + area.getEMD2();
            } else if (area.getEMD2() != null) {
                title = area.getEMD2();
                subtitle = area.getSD() + " " + area.getEMD1();
            }

            result.add(new WeatherLocationDTO(title, subtitle, area.getAreaId(), null, area.getLatitude(), area.getLongitude(), null));
        }
        return result;
    }

}
