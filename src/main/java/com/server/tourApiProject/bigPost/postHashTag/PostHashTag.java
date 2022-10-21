package com.server.tourApiProject.bigPost.postHashTag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.bigPost.post.Post;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "postHashTag")
/**
* @className : PostHashTag.java
* @description : 게시물 해시태그 클래스 입니다.
* @modification : 2022-08-08(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-08
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-08       jinhyeok       주석 수정

 */
public class PostHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postHashTagListId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", insertable = false, updatable=false)
    private Post post;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long hashTagId;

    @Column(nullable = false)
    private String hashTagName;
}
