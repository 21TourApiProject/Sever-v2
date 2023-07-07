package com.server.tourApiProject.star.starFeature;

import com.server.tourApiProject.hashTag.HashTagCategory;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="starFeature")
/**
 * className : com.server.tourApiProject.star.starFeature
 * description : 설명
 * modification : 2023-07-06(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-07-06
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-07-06       jinhyeok       최초생성
 */
public class StarFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long starFeatureId;

    @Column(nullable = false, unique = true)
    private String starFeatureName;
}
