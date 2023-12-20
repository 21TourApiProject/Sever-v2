package com.server.tourApiProject.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.alarm.Alarm;
import com.server.tourApiProject.bigPost.post.Post;
import com.server.tourApiProject.bigPost.postComment.PostComment;
import com.server.tourApiProject.interestArea.InterestArea;
import com.server.tourApiProject.fcm.FcmToken;
import com.server.tourApiProject.likes.Likes;
import com.server.tourApiProject.myHashTag.MyHashTag;
import com.server.tourApiProject.myWish.MyWish;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")

/**
 * @className : User.java
 * @description : 사용자 엔티티 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String realName;

    @Column
    private Boolean sex; //남자 0, 여자 1

    @Column
    private String birthDay;

    @Column(unique = true, length = 16)
    private String mobilePhoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String encryptedPassword; //암호화한 password 값

    @Column(nullable = false, unique = true, length = 20)
    private String nickName;

    @Column
    private String profileImage;

    @Column
    private String ageRange; //연령대

    @Column
    private Boolean isMarketing; //마케팅 정보 수신 동의

    @Column
    private Boolean kakao;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> myPosts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MyHashTag> myHashTags = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MyWish> myWishes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Likes> myLike = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostComment> myComment = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<InterestArea> myInterestArea = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Alarm> myAlarm = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FcmToken> myFcmTokens = new ArrayList<>();

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime signUpDt;

}
