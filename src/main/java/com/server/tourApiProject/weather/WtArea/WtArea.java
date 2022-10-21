package com.server.tourApiProject.weather.WtArea;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wtArea")

/**
* @className : WtArea.java
* @description : 날씨 Entity 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class WtArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wtAreaId;

    @Column(nullable = false)
    private String cityName;    //도시 이름

    @Column(nullable = false)
    private String provName;    //지역 이름

    @Column(nullable = false)
    private Double latitude;    //위도

    @Column(nullable = false)
    private Double longitude;    //경도

    @Column(nullable = false)
    private Double minLightPol;    //광공해 최소

    @Column(nullable = false)
    private Double maxLightPol;    //광공해 최대

}
