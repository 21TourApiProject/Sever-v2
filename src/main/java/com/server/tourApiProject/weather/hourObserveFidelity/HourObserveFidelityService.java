package com.server.tourApiProject.weather.hourObserveFidelity;

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

public class HourObserveFidelityService {

    private final HourObserveFidelityRepository hourObserveFidelityRepository;

    public Integer getHourObserveFidelity(String date, Integer hour) {
        HourObserveFidelity byDateAndHour = hourObserveFidelityRepository.findByDateAndHour(date, hour);
        return byDateAndHour.getObserveFidelity();
    }

    public List<HourObserveFidelityParams> getHourObserveFidelityList(String date, Integer hour) {

        List<HourObserveFidelityParams> result = new ArrayList<>();

        List<HourObserveFidelity> byDate = hourObserveFidelityRepository.findByDate(date);
        for(int i = hour; i<= 23;i++){
//            result.add(new HourObserveFidelityParams(i, ));
        }

        return result;

    }
}
