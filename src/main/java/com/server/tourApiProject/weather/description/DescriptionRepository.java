package com.server.tourApiProject.weather.description;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description, Long> {
    Description findById(String id);
}
