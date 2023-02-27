package com.server.tourApiProject.star.starHashTag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.bigPost.post.Post;
import com.server.tourApiProject.star.constellation.Constellation;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "starHashTag")
/**
 * className : com.server.tourApiProject.star.starHashTag
 * description : 설명
 * modification : 2022-12-27(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2022-12-27
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2022-12-27       jinhyeok       최초생성
 */

public class StarHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long starHashTagListId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constId", insertable = false, updatable=false)
    private Constellation constellation;

    @Column(nullable = false)
    private Long constId;

    @Column(nullable = false)
    private Long hashTagId;

    @Column(nullable = false)
    private String hashTagName;
}
