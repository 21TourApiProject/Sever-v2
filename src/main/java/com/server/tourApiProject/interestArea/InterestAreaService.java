package com.server.tourApiProject.interestArea;

import com.server.tourApiProject.interestArea.model.*;
import com.server.tourApiProject.observation.observeImage.ObserveImage;
import com.server.tourApiProject.observation.observeImage.ObserveImageRepository;
import com.server.tourApiProject.weather.area.WeatherArea;
import com.server.tourApiProject.weather.area.WeatherAreaRepository;
import com.server.tourApiProject.weather.observation.WeatherObservation;
import com.server.tourApiProject.weather.observation.WeatherObservationRepository;
import com.server.tourApiProject.weather.observationalFit.ObservationalFitRepository;
import com.server.tourApiProject.weather.observationalFit.ObservationalFitService;
import com.server.tourApiProject.weather.observationalFit.model.AreaTimeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.server.tourApiProject.weather.common.CommonService.getObservationalFitDBBaseDate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

public class InterestAreaService {

    private final ObservationalFitService observationalFitService;
    private final InterestAreaRepository interestAreaRepository;
    private final WeatherAreaRepository weatherAreaRepository;
    private final WeatherObservationRepository weatherObservationRepository;
    private final ObservationalFitRepository observationalFitRepository;
    private final ObserveImageRepository observeImageRepository;

    public List<InterestAreaDTO> getInterestArea(Long userId) {

        List<InterestAreaDTO> result = new ArrayList<>();
        List<AreaTimeDTO> areaTimeList = new ArrayList<>();

        for (InterestArea interestArea : interestAreaRepository.findByUserId(userId)) {
            InterestAreaDTO interestAreaDTO = InterestAreaDTO.builder()
                    .regionId(interestArea.getRegionId())
                    .regionName(interestArea.getRegionName())
                    .regionType(interestArea.getRegionType()).build();

            if (RegionType.OBSERVATION.getValue() == interestArea.getRegionType()) {
                // 관측지 이미지
                List<ObserveImage> imageList = observeImageRepository.findByObservationId(interestArea.getRegionId());
                if (!imageList.isEmpty()) {
                    interestAreaDTO.setRegionImage(imageList.get(0).getImage());
                }

                // 관측지 관측적합도
                observationalFitRepository.findByDateAndObservationCode(getObservationalFitDBBaseDate(), interestArea.getRegionId())
                        .ifPresent(observationalFit -> interestAreaDTO.setObservationalFit(String.valueOf(Math.round(observationalFit.getBestObservationalFit()))));

                result.add(interestAreaDTO);
            } else if (RegionType.AREA.getValue() == interestArea.getRegionType()) {
                WeatherArea area = weatherAreaRepository.findById(interestArea.getRegionId()).get();
                areaTimeList.add(AreaTimeDTO.builder()
                        .date(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .hour(Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH"))))
                        .lat(area.getLatitude())
                        .lon(area.getLongitude())
                        .areaId(interestArea.getRegionId())
                        .build());
                result.add(interestAreaDTO);
            }
        }

        if (!areaTimeList.isEmpty()) {
            List<String> observationalFitList = observationalFitService.getInterestAreaInfo(areaTimeList).collectList().block();
            int idx = 0;
            for (InterestAreaDTO interestAreaDTO : result) {
                if (RegionType.AREA.getValue() == interestAreaDTO.getRegionType()) {
                    interestAreaDTO.setObservationalFit(observationalFitList.get(idx++));
                }
            }
        }
        return result;
    }

    public void addInterestArea(AddInterestAreaDTO addInterestAreaDTO) {
        InterestArea interestArea = InterestArea.builder()
                .userId(addInterestAreaDTO.getUserId())
                .regionId(addInterestAreaDTO.getRegionId())
                .regionName(addInterestAreaDTO.getRegionName())
                .regionType(addInterestAreaDTO.getRegionType()).build();
        interestAreaRepository.save(interestArea);
    }

    public void deleteInterestArea(Long userId, Long regionId, Integer regionType) {
        Optional<InterestArea> interestArea = interestAreaRepository.findByUserIdAndRegionIdAndRegionType(userId, regionId, regionType);
        interestAreaRepository.delete(interestArea.get());
    }

    /**
     * @param regionId   : 관측지 또는 지역 id
     * @param regionType : 1 = 관측지, 2 = 지역
     */
    public InterestAreaDetailDTO getInterestAreaDetailInfo(Long regionId, Integer regionType) {

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Integer hour = Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));

        AreaTimeDTO areaTimeDTO = new AreaTimeDTO();
        areaTimeDTO.setDate(date);
        areaTimeDTO.setHour(hour);

        InterestAreaDetailDTO result = new InterestAreaDetailDTO();
        result.setRegionId(regionId);
        result.setRegionType(regionType);

        if (RegionType.OBSERVATION.getValue() == regionType) {
            WeatherObservation region = weatherObservationRepository.findById(regionId).get();
            result.setRegionName(region.getName());
            result.setLatitude(region.getLatitude());
            result.setLongitude(region.getLongitude());

            areaTimeDTO.setLat(region.getLatitude());
            areaTimeDTO.setLon(region.getLongitude());
            areaTimeDTO.setObservationId(regionId);

            List<ObserveImage> imageList = observeImageRepository.findByObservationId(regionId);
            if (!imageList.isEmpty()) {
                result.setRegionImage(imageList.get(0).getImage());
            }
        } else if (RegionType.AREA.getValue() == regionType) {
            WeatherArea region = weatherAreaRepository.findById(regionId).get();
            result.setRegionName(region.getEMD2());
            result.setLatitude(region.getLatitude());
            result.setLongitude(region.getLongitude());

            areaTimeDTO.setLat(region.getLatitude());
            areaTimeDTO.setLon(region.getLongitude());
            areaTimeDTO.setAreaId(regionId);
        }
        InterestAreaDetailWeatherInfo interestAreaDetailWeatherInfo = observationalFitService.getInterestAreaDetailInfo(areaTimeDTO).block();
        result.setInterestAreaDetailWeatherInfo(interestAreaDetailWeatherInfo);

        // 관측지 최대 관측적합도 갱신
        if (RegionType.OBSERVATION.getValue() == regionType) {
            observationalFitRepository.findByDateAndObservationCode(getObservationalFitDBBaseDate(), regionId)
                    .ifPresent(observationalFit -> {
                        if (interestAreaDetailWeatherInfo.getBestObservationalFit() != null)
                            observationalFit.setBestObservationalFit(Double.valueOf(interestAreaDetailWeatherInfo.getBestObservationalFit()));
                    });
        }

        return result;
    }
}
