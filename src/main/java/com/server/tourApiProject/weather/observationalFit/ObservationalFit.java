package com.server.tourApiProject.weather.observationalFit;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weather_observational_fit")
public class ObservationalFit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long observationFitId;

    @Column(nullable = false)
    private Long observationCode; // 관측지 id

    @Column(nullable = false)
    private String date; // 20221103

    @Column(nullable = false)
    private Double bestObservationFit; // 관측적합도
}
