package com.server.tourApiProject.weather.WtToday;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wtToday")

/**
* @className : WtToday.java
* @description : 오늘의 날씨 Entity 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class WtToday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wtTodayId;

    @Column(nullable = false)
    private int todayWtId;    //오늘의 날씨 아이디

    @Column(nullable = false)
    private String todayWtName1;    //오늘의 날씨 이름

    private String todayWtName2;    //오늘의 날씨 이름(밑에 줄)
}
