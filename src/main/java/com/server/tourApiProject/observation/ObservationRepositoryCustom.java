package com.server.tourApiProject.observation;

import com.server.tourApiProject.observation.model.ObservationSimpleParams;

import java.util.List;

public interface ObservationRepositoryCustom {
    List<ObservationSimpleParams> findObservationSimpleByIdList(List<Long> observationIds, String date);
}
