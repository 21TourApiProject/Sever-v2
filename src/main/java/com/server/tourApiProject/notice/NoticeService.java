package com.server.tourApiProject.notice;

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
    public void createNotice(NoticeParams noticeParams) {

        Notice notice = new Notice();
        notice.setNoticeTitle(noticeParams.getNoticeTitle());
        notice.setNoticeContent(noticeParams.getNoticeContent());
        notice.setNoticeDate(noticeParams.getNoticeDate());
        noticeRepository.save(notice);
    }
}
