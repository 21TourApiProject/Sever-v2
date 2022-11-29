package com.server.tourApiProject.weather.hourObserveFidelity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name="hourObserveFidelity")

/**
 * @className : HourObserveFidelity.java
 * @description : 시간별 관측적합도 엔티티 입니다.
 * @modification : 2022-11-03(sein) 수정
 * @author : sein
 * @date : 2022-11-03
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-11-03     sein        주석 생성

 */
public class HourObserveFidelity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hourObserveFidelityId;

    @Column(nullable = false)
    private String date; //20221103

    @Column(nullable = false)
    private Integer hour; // 0 ~ 6, 18 ~ 24

    @Column(nullable = false)
    private Integer observeFidelity; // 관측적합도 값
}
