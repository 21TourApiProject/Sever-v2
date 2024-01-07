package com.server.tourApiProject.alarm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="alarm")
/**
* @className : Alarm.java
* @description : 알림 게시판 클래스 입니다.
* @modification : 2022-08-10(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-10
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-10       jinhyeok       주석 수정

 */
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @Column(nullable = false)
    private String alarmTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String alarmContent;

    @Column(nullable = false)
    private String alarmDate;

    @Column
    private String isNotice;

    @Column
    private Long itemId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable=false)
    private User user;

    @Column()
    private Long userId;

}
