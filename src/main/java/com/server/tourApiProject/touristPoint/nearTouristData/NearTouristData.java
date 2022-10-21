package com.server.tourApiProject.touristPoint.nearTouristData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.touristPoint.touristData.TouristData;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor

/**
 * @className : NearTouristData.java
 * @description : 근처 관광지 엔티티 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
@Table(name="nearTouristData")
public class NearTouristData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nearTouristDataId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentId", insertable = false, updatable=false)
    private TouristData touristData;

    @Column(nullable = false)
    private Long touristDataId;

    @Column(nullable = false)
    private Long contentId; //콘텐츠 ID

    @Column
    private String firstImage; //대표이미지 원본

    @Column
    private String title; //제목

    @Column
    private String addr; //주소

    @Column
    private String cat3Name; //소분류 이름

    @Column
    private String overviewSim; //개요 한줄



}
