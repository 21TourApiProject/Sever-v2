package com.server.tourApiProject.interestArea;

import com.server.tourApiProject.observation.observeImage.ObserveImage;
import com.server.tourApiProject.observation.observeImage.ObserveImageRepository;
import com.server.tourApiProject.weather.area.WeatherArea;
import com.server.tourApiProject.weather.area.WeatherAreaRepository;
import com.server.tourApiProject.weather.observation.WeatherObservation;
import com.server.tourApiProject.weather.observation.WeatherObservationRepository;
import com.server.tourApiProject.weather.observationalFit.ObservationalFitService;
import com.server.tourApiProject.weather.observationalFit.model.AreaTimeDTO;
import com.server.tourApiProject.weather.observationalFit.model.WeatherInfo;
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
        for (InterestArea interestArea : interestAreaRepository.findByUserId(userId)) {

            AreaTimeDTO areaTimeDTO = new AreaTimeDTO();
            areaTimeDTO.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            areaTimeDTO.setHour(Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH"))));

            if (interestArea.getRegionType() == 1) { // 관측지
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

            result.add(InterestAreaDTO.builder().regionName(interestArea.getRegionName())
                    .regionId(interestArea.getRegionId())
                    .regionType(interestArea.getRegionType())
                    .observationalFit(observationalFitService.getInterestAreaInfo(areaTimeDTO).block()).build());
        }
        return result;
    }

    public void addInterestArea(UpdateInterestAreaDTO updateInterestAreaDTO) {
        InterestArea interestArea = InterestArea.builder()
                .userId(updateInterestAreaDTO.getUserId())
                .regionId(updateInterestAreaDTO.getRegionId())
                .regionName(updateInterestAreaDTO.getRegionName())
                .regionType(updateInterestAreaDTO.getRegionType()).build();
        interestAreaRepository.save(interestArea);
    }

    public void deleteInterestArea(UpdateInterestAreaDTO updateInterestAreaDTO) {
        Optional<InterestArea> interestArea = interestAreaRepository.findByUserIdAndRegionId(updateInterestAreaDTO.getUserId(), updateInterestAreaDTO.getRegionId());
        interestAreaRepository.delete(interestArea.get());
    }

    /**
     * @param regionId   : 관측지 또는 지역 id
     * @param regionType : 1 = 관측지, 2 = 지역
     */
    public InterestAreaWeatherDTO getInterestAreaInfo(Long regionId, Integer regionType) {

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Integer hour = Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));

        AreaTimeDTO areaTimeDTO = new AreaTimeDTO();
        areaTimeDTO.setDate(date);
        areaTimeDTO.setHour(hour);

        InterestAreaWeatherDTO result = new InterestAreaWeatherDTO();
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
            result.setRegionImage("http://tong.visitkorea.or.kr/cms/resource/00/2431800_image2_1.jpg");
        } else { // 지역
            WeatherArea region = weatherAreaRepository.findById(regionId).get();
            result.setRegionName(region.getEMD2());
            result.setLatitude(region.getLatitude());
            result.setLongitude(region.getLongitude());

            areaTimeDTO.setLat(region.getLatitude());
            areaTimeDTO.setLon(region.getLongitude());
            areaTimeDTO.setAreaId(regionId);
        }

        WeatherInfo weatherInfo = observationalFitService.getWeatherInfo(areaTimeDTO).block();
        result.setBestDay(weatherInfo.getTodayComment1().substring(0, 2));
        result.setBestHour(weatherInfo.getBestTime());
        result.setBestObservationalFit(weatherInfo.getBestObservationalFit());
        result.setWeatherReport("날씨 요약 레포트 날씨 요약 레포트 날씨 요약 레포트 날씨 요약 레포트 날씨 요약 레포트 날씨 요약 레포트 날씨 요약 레포트 날씨 요약 레포트 ");
        return result;
    }
}
