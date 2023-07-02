package com.server.tourApiProject.weather.observation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherObservationRepository extends JpaRepository<WeatherObservation, Long> {
    WeatherObservation findByLatitudeAndLongitude(Double latitude, Double longitude);
}
