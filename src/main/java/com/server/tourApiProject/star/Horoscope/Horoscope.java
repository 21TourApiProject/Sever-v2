package com.server.tourApiProject.star.Horoscope;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "horoscope")

/**
* @className : Horoscope.java
* @description : 별자리 운세 Entity 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class Horoscope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long horId;

    @Column(nullable = false)
    private String horImage;  //별자리 이미지

    @Column(nullable = false)
    private String horEngTitle;  //별자리 영어 이름

    @Column(nullable = false)
    private String horKrTitle;  //별자리 한국 이름

    @Column(nullable = false)
    private String horPeriod;   //별자리 기간

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc1;    //1월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc2;    //2월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc3;    //3월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc4;    //4월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc5;    //5월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc6;    //6월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc7;    //7월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc8;    //8월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc9;    //9월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc10;   //10월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc11;   //11월 별자리 운세

    @Column(nullable = false, columnDefinition = "TEXT")
    private String horDesc12;   //12월 별자리 운세

    private String horGuard; // 수호성

    @Column(columnDefinition = "TEXT")
    private String horPersonality; // 별자리 성격

    @Column(columnDefinition = "TEXT")
    private String horTravel; // 여행 취향

}
