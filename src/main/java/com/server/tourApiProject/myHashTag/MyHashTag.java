package com.server.tourApiProject.myHashTag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.user.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="myHashTag")

/**
 * @className : MyHashTag.java
 * @description : 사용자가 관심있는 해시태그로 선택한 엔티티 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class MyHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myHashTagListId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable=false)
    private User user;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long hashTagId;

    @Column(nullable = false)
    private String hashTagName;
}
