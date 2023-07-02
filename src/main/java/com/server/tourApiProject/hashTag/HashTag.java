package com.server.tourApiProject.hashTag;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="hashTag")

/**
 * @className : HashTag.java
 * @description : HashTag 엔티티 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashTagId;

    @Column(nullable = false, unique = true)
    private String hashTagName;

    @Column
    @Enumerated(EnumType.STRING)
    private HashTagCategory category;

}
