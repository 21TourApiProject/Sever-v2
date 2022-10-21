package com.server.tourApiProject.touristPoint.contentType;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="contentType")

/**
 * @className : ContentType.java
 * @description : 타입 정보 엔티티 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class ContentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentTypeId;

    @Column(nullable = false)
    private Integer contentType; //12, 39

    @Column(nullable = false)
    private String contentName; //관광지, 음식

    @Column(nullable = false)
    private String cat1Code; //대분류 코드

    @Column(nullable = false)
    private String cat1Name; //대분류 이름

    @Column(nullable = false)
    private String cat2Code; //중분류 코드

    @Column(nullable = false)
    private String cat2Name; //중분류 이름

    @Column(nullable = false)
    private String cat3Code; //소분류 코드

    @Column(nullable = false)
    private String cat3Name; //소분류 이름


}
