package com.server.tourApiProject.touristPoint.touristDataHashTag;

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
@Table(name="touristDataHashTag")

/**
 * @className : TouristDataHashTag.java
 * @description : 관광지 해시태그 엔티티 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class TouristDataHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long touristDataHashTagId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentId", insertable = false, updatable=false)
    private TouristData touristData;

    @Column(nullable = false)
    private Long contentId;

    @Column(nullable = false)
    private Long hashTagId;

    @Column(nullable = false)
    private String hashTagName;
}
