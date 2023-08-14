package com.server.tourApiProject.bigPost.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.bigPost.postComment.PostComment;
import com.server.tourApiProject.bigPost.postHashTag.PostHashTag;
import com.server.tourApiProject.bigPost.postImage.PostImage;
import com.server.tourApiProject.observation.Observation;
import com.server.tourApiProject.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="post")
/** 
* @className : Post.java
* @description : 게시물 클래스 입니다.
* @modification : 2022-08-05(jinhyeok) 주석 수정
* @author : jinhyeok  
* @date : 2022-08-05   
* @version : 1.0 
   ====개정이력(Modification Information)====      
  수정일        수정자        수정내용   
   -----------------------------------------    
   2022-08-05       jinhyeok       주석 수정
 
 */
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 1000,nullable = false)
    private String postContent;

    @Column(length = 1000,nullable = false)
    private String postTitle;

    @Column
    private String optionObservation;

    @Column
    private String optionHashTag;
    @Column
    private String optionHashTag2;
    @Column
    private String optionHashTag3;
    @Column
    private String optionHashTag4;
    @Column
    private String optionHashTag5;
    @Column
    private String optionHashTag6;
    @Column
    private String optionHashTag7;
    @Column
    private String optionHashTag8;
    @Column
    private String optionHashTag9;
    @Column
    private String optionHashTag10;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate yearDate;

    @Column
    @DateTimeFormat(pattern = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime time;

    @Column
    @DateTimeFormat(pattern = "MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate writeDate;

    @Column
    @DateTimeFormat(pattern = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime writeTime;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostHashTag> postHashTags=new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostComment> postComments=new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostImage> postImages=new ArrayList<>();

    @Column
    private Long saved;     //저장횟수


    @Column
    private Long liked;     //좋아요수

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable=false)
    private User user;

    @Column(nullable = false)
    private Long userId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "observationId", insertable = false, updatable=false)
    private Observation observation;

    @Column(nullable = false)
    private Long observationId;

    @Column
    private Long areaCode;  //지역코드
}
