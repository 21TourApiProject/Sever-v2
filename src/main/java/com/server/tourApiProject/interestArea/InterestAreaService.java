package com.server.tourApiProject.interestArea;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

public class InterestAreaService {
    private final InterestAreaRepository interestAreaRepository;

    public List<InterestArea> getAllInterestArea(Long userId) {
        return interestAreaRepository.findByUserId(userId);
    }

    public void addInterestArea(InterestAreaParams interestAreaParams) {
        InterestArea interestArea = InterestArea.builder()
                .userId(interestAreaParams.getUserId())
                .observationId(interestAreaParams.getObservationId())
                .observationName(interestAreaParams.getObservationName()).build();
        interestAreaRepository.save(interestArea);
    }

    public void deleteInterestArea(InterestAreaParams interestAreaParams) {

        Optional<InterestArea> interestArea = interestAreaRepository.findByUserIdAndObservationId(interestAreaParams.getUserId(), interestAreaParams.getObservationId());
        interestAreaRepository.delete(interestArea.get());
    }
}
