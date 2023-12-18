package com.server.tourApiProject.notice;

import com.server.tourApiProject.alarm.Alarm;
import com.server.tourApiProject.alarm.AlarmService;
import com.server.tourApiProject.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

/**
 * @className : NoticeService.java
 * @description : Notice service 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final FcmService fcmService;
    private final AlarmService alarmService;

    /**
     * description: 모든 공지사항 조회
     *
     * @return Notice list
     */
    public List<Notice> getAllNotice() {
        return noticeRepository.findAll();
    }

    /**
     * description: 공지사항 id에 해당하는 공지사항 정보 조회
     *
     * @param noticeId - 공지사항 id
     * @return Notice Entity
     */
    public Notice getNotice(Long noticeId) {
        return noticeRepository.findById(noticeId).orElseThrow(IllegalAccessError::new);
    }

    /**
     * description: 공지사항 생성
     *
     * @param noticeParams
     */
    public void createNotice(NoticeParams noticeParams) throws InterruptedException {

        Notice notice = new Notice();
        notice.setNoticeTitle(noticeParams.getNoticeTitle());
        notice.setNoticeContent(noticeParams.getNoticeContent());
        notice.setNoticeDate(noticeParams.getNoticeDate());
        System.out.println(noticeParams.getNoticeTitle()+"그리고" + noticeParams.getNoticeContent());
        noticeRepository.save(notice);
        List<String> tokenList = fcmService.getAllFcmToken(); //푸쉬 알람 관련 코드
        fcmService.sendMessageAll(tokenList,notice.getNoticeTitle(),notice.getNoticeContent());
        Alarm alarm =new Alarm();
        alarm.setAlarmContent(notice.getNoticeContent());
        alarm.setAlarmTitle(notice.getNoticeTitle());
        alarm.setAlarmDate(notice.getNoticeDate());
        alarm.setUserId(null);
        alarm.setIsNotice("notice");
        alarm.setItemId(notice.getNoticeId());
        alarmService.createAlarm(alarm);
    }

    /**
     * description: 공지사항 삭제
     *
     * @param noticeId
     */
    public void deleteNoticewithId(Long noticeId) {

        noticeRepository.deleteById(noticeId);
    }

    /**
     * description: 공지사항 수정
     *
     * @param noticeparam
     */
    public void updateNotice(NoticeUpdateParam noticeparam) {

        Optional<Notice> notice = noticeRepository.findById(noticeparam.getNoticeId());

        notice.ifPresent(notice1 -> {
            notice1.setNoticeTitle(noticeparam.getNoticeTitle());
            notice1.setNoticeContent(noticeparam.getNoticeContent());
            noticeRepository.save(notice1);
        });
    }
}
