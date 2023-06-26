package com.server.tourApiProject.weather.description;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "weather_description")
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long descriptionId;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String main; // 메인 분류

    @Column(nullable = false)
    private String description; // 상세 분류

    @Column(nullable = false)
    private String result;

}
