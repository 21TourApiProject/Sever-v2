package com.server.tourApiProject.interestArea;

import com.server.tourApiProject.interestArea.model.AddInterestAreaDTO;
import com.server.tourApiProject.interestArea.model.InterestAreaDTO;
import com.server.tourApiProject.interestArea.model.InterestAreaDetailDTO;
import com.server.tourApiProject.observation.observeImage.ObserveImage;
import com.server.tourApiProject.observation.observeImage.ObserveImageRepository;
import com.server.tourApiProject.weather.area.WeatherArea;
import com.server.tourApiProject.weather.area.WeatherAreaRepository;
import com.server.tourApiProject.weather.observation.WeatherObservation;
import com.server.tourApiProject.weather.observation.WeatherObservationRepository;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

public class InterestAreaService {
    private final InterestAreaRepository interestAreaRepository;
    private final ObservationalFitService observationalFitService;
    private final WeatherAreaRepository weatherAreaRepository;
    private final WeatherObservationRepository weatherObservationRepository;
    private final ObserveImageRepository observeImageRepository;

    public List<InterestAreaDTO> getAllInterestArea(Long userId) {

        List<InterestAreaDTO> result = new ArrayList<>();
        List<AreaTimeDTO> areaTimeList = new ArrayList<>();

        for (InterestArea interestArea : interestAreaRepository.findByUserId(userId)) {

            InterestAreaDTO interestAreaDTO = InterestAreaDTO.builder().regionName(interestArea.getRegionName())
                    .regionId(interestArea.getRegionId())
                    .regionType(interestArea.getRegionType()).build();

            AreaTimeDTO areaTimeDTO = new AreaTimeDTO();
            areaTimeDTO.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            areaTimeDTO.setHour(Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH"))));

            if (interestArea.getRegionType() == 1) { // 관측지

                // 관측지 이미지
                List<ObserveImage> imageList = observeImageRepository.findByObservationId(interestArea.getRegionId());
                if (!imageList.isEmpty()) {
                    interestAreaDTO.setRegionImage(imageList.get(0).getImage());
                }

                areaTimeDTO.setObservationId(interestArea.getRegionId());
                WeatherObservation observation = weatherObservationRepository.findById(interestArea.getRegionId()).get();
                areaTimeDTO.setLat(observation.getLatitude());
                areaTimeDTO.setLon(observation.getLongitude());
            } else { // 지역
                WeatherArea area = weatherAreaRepository.findById(interestArea.getRegionId()).get();
                areaTimeDTO.setAreaId(interestArea.getRegionId());
                areaTimeDTO.setLat(area.getLatitude());
                areaTimeDTO.setLon(area.getLongitude());
            }
            areaTimeList.add(areaTimeDTO);
            result.add(interestAreaDTO);
        }

        List<String> observationalFitList = observationalFitService.getInterestAreaInfo(areaTimeList).collectList().block();
        for (int i = 0; i < observationalFitList.size(); i++) {
            result.get(i).setObservationalFit(observationalFitList.get(i));
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

        if (regionType == 1) { // 관측지
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
        } else { // 지역
            WeatherArea region = weatherAreaRepository.findById(regionId).get();
            result.setRegionName(region.getEMD2());
            result.setLatitude(region.getLatitude());
            result.setLongitude(region.getLongitude());

            areaTimeDTO.setLat(region.getLatitude());
            areaTimeDTO.setLon(region.getLongitude());
            areaTimeDTO.setAreaId(regionId);
        }

        result.setInterestAreaDetailWeatherInfo(observationalFitService.getInterestAreaDetailInfo(areaTimeDTO).block());
        return result;
    }
}
