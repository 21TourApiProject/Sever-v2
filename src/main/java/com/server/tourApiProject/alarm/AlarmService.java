package com.server.tourApiProject.alarm;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.server.tourApiProject.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
/**
* @className : AlarmService.java
* @description : 알림 게시판 Service 입니다. (알림 생성, 알림 목록 가져오기)
* @modification : 2022-08-10(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-10
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-10       jinhyeok       주석 수정

 */
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final FcmService fcmService;

    /**
     * description: 알림 목록 가져오는 메소드.
     *
     * @return the all alarm
     */
    public List<Alarm> getAllAlarm() {
        return alarmRepository.findAll();
    }

    /**
     *  description: 알림 생성하는 메소드.
     *
     * @param alarm the alarm
     */
    public void createAlarm(Alarm alarm) throws InterruptedException {
        alarmRepository.save(alarm);
        List<String> tokenList = fcmService.getAllFcmToken();
        fcmService.sendMessageAll(tokenList,alarm.getAlarmTitle(),alarm.getAlarmContent());
    }
}
