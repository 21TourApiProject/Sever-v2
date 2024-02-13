package com.server.tourApiProject.observation;

import com.server.tourApiProject.observation.model.ObservationParams;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ObservationServiceTest {

    @InjectMocks
    ObservationService observationService;

    @Mock
    ObservationRepository observationRepository;

    @Captor
    ArgumentCaptor<Observation> observationCaptor;

    static final String OBSERVATION_NAME = "observationName";

    @Test
    void getAllObservationTest() {
        List<Observation> observations = Arrays.asList(new Observation());
        System.out.println(observations);
        given(observationRepository.findAll()).willReturn(observations);
        List<Observation> result = observationService.getAllObservation();
        Assertions.assertThat(result).isEqualTo(observations);
    }

    @Test
    void createObservationTest() {
        ObservationParams observationParams = ObservationParams.builder()
                .observationName(OBSERVATION_NAME)
                .address("address")
                .entranceFee("2000")
                .observeType("관측지")
                .outline("개요")
                .latitude(10.00)
                .longitude(10.00)
                .parking("없음")
                .link("www.link.com")
                .phoneNumber("031-111-1111")
                .operatingHour("11~12").build();

        observationService.createObservation(observationParams);

        verify(observationRepository).save(observationCaptor.capture());
        Observation savedResult = observationCaptor.getValue();
        Assertions.assertThat(savedResult.getSaved()).isEqualTo(0);
        Assertions.assertThat(savedResult.getObservationName()).isEqualTo(observationParams.getObservationName());
    }

}
