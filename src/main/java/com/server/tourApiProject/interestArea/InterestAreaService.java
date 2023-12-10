package com.server.tourApiProject.interestArea;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

public class InterestAreaService {
    private final InterestAreaRepository interestAreaRepository;

    public List<InterestAreaDTO> getAllInterestArea(Long userId) {

        List<InterestAreaDTO> result = new ArrayList<>();
        for (InterestArea interestArea : interestAreaRepository.findByUserId(userId)) {

            String observationalFit = "";
            if(interestArea.getRegionType() == 1){ // 관측지

            } else { // 지역

            }
            result.add(InterestAreaDTO.builder().regionName(interestArea.getRegionName())
                    .regionId(interestArea.getRegionId())
                    .regionType(interestArea.getRegionType())
                    .observationalFit("59").build());
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
}
