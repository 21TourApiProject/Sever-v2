package com.server.tourApiProject.weather.dayObserveFidelity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

public class DayObserveFidelityService {

    private final DayObserveFidelityRepository dayObserveFidelityRepository;

    public Integer getDayObserveFidelity(String date) {
        DayObserveFidelity byDate = dayObserveFidelityRepository.findByDate(date);
        return byDate.getObserveFidelity();
    }
}
