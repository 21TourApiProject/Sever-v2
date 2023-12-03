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
    private Long observationalFitId;

    @Column(nullable = false)
    private Long observationCode; // 관측지 id

    @Column(nullable = false)
    private String date; // 2022-11-03

    @Column(nullable = false)
    private Double bestObservationalFit; // 관측적합도
}
