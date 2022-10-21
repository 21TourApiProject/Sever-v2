package com.server.tourApiProject.touristPoint.area;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="area")

/**
 * @className : Area.java
 * @description : 지역 정보 엔티티 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long areaId;

    @Column(nullable = false)
    private Long areaCode; //지역코드

    @Column(nullable = false)
    private String areaName; //지역이름

    @Column(nullable = false)
    private Long sigunguCode; //시군구코드

    @Column(nullable = false)
    private String sigunguName; //시군구이름
}
