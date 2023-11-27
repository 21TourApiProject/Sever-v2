package com.server.tourApiProject.weather.observationalFit;

import java.util.List;

public interface ObservationalFitRepositoryCustom {

    List<Long> getObservationIdsByBestFit(String date);
}
