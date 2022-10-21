package com.server.tourApiProject.alarm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(nullable = false)
    private String alarmContent;

    @Column(nullable = false)
    private String alarmDate;
}
