package com.server.tourApiProject.likes;

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
@Table(name="likes")

/**
 * className : com.server.tourApiProject.like
 * description : 설명
 * modification : 2023-02-08(jinhyeok) like 생성
 * author : jinhyeok
 * date : 2023-02-08
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-02-08       jinhyeok       최초생성
 */
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable=false)
    private User user;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer likeType; //0이면 관측지, 1이면 관광지, 2면 게시물

    @Column
    private Long itemId; //관측지 id 또는 관광지 id 또는 게시물 id

    @Column(nullable = false)
    private Long likeTime; //찜한 시간
}
