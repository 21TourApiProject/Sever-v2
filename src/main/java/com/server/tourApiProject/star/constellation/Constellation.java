package com.server.tourApiProject.star.constellation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;


@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "constellation")

/**
* @className : Constellation.java
* @description : 별자리 Entity 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class Constellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long constId;

    @Column(nullable = false)
    private String constName;  //별자리 이름

    @Column(nullable = false, columnDefinition = "TEXT")
    private String constStory;    // 별자리 설화

    @Column(nullable = false, columnDefinition = "TEXT")
    private String constMtd;    // 별자리 관측법

    @Column(nullable = false)
    private String constBestMonth; // 가장 보기 좋은 달

    private String constFeature1; // 별자리 특징(배너 형식)

    private String constFeature2; // 별자리 특징(배너 형식)

    private String constFeature3; // 별자리 특징(배너 형식)

    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd", timezone = "Asia/Seoul")
    private String startDate1;    // 별자리가 보이기 시작하는 날짜

    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd", timezone = "Asia/Seoul")
    private String endDate1;    // 별자리가 보이기 끝나는 날짜

    @DateTimeFormat(pattern = "MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd", timezone = "Asia/Seoul")
    private String startDate2;    // 별자리가 보이기 시작하는 날짜

    @DateTimeFormat(pattern = "MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd", timezone = "Asia/Seoul")
    private String endDate2;    // 별자리가 보이기 끝나는 날짜

    @Column(nullable = false)
    private String constEng;  //별자리 이름
}
