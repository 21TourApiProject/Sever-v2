package com.server.tourApiProject.observation.observeImage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.observation.Observation;
import lombok.*;

import javax.persistence.*;

/**
* @className : ObserveImage.java
* @description : 관픅지 이미지 엔티티
* @modification : 2022-08-29 (gyul chyoung) 주석추가
* @author : gyul chyoung
* @date : 2022-08-29
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-29       gyul chyoung       주석추가
 */

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "observeImage")
public class ObserveImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long observeImageListId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "observationId", insertable = false, updatable=false)
    private Observation observation;

    @Column(nullable = false)
    private Long observationId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String image;   // 이미지 경로

    @Column(columnDefinition = "TEXT")
    private String imageSource; //이미지 출저

}
