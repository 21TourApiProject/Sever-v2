package com.server.tourApiProject.myWish;

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
@Table(name="myWish")

/**
 * @className : MyWish.java
 * @description : 사용자가 찜한 관측지 또는 관광지 엔티티 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class MyWish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myWishId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable=false)
    private User user;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer wishType; //0이면 관측지, 1이면 관광지, 2면 게시물

    @Column
    private Long itemId; //관측지 id 또는 관광지 id 또는 게시물 id

    @Column(nullable = false)
    private Long wishTime; //찜한 시간

}
