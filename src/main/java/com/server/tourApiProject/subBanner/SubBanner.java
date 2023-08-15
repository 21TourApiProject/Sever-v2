package com.server.tourApiProject.subBanner;


import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subBanner")
/**
 * className : com.server.tourApiProject.subBanner
 * description : 메인 페이지 서브 배너 Entity
 * modification : 2023-08-15(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-08-15
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-08-15       jinhyeok       최초생성
 */
public class SubBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subBannerId;

    @Column
    private String bannerImage;  //배너 이미지

    @Column
    private String link;    // 연결된 링크

    @Column
    private Boolean isShow; //보여줄지 선택
}
