package com.server.tourApiProject.notice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"10.1 공지사항"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor

/**
 * @className : NoticeController.java
 * @description : Notice controller 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class NoticeController {
    private final NoticeService noticeService;

    @ApiOperation(value = "공지사항 입력", notes = "공지사항을 입력한다")
    @PostMapping(value = "notice/")
    public void createNotice(@RequestBody NoticeParams noticeParams) throws InterruptedException{
        System.out.println(noticeParams.getNoticeTitle());
        noticeService.createNotice(noticeParams);
    }

    @ApiOperation(value = "모든 공지사항 조회", notes = "모든 공지사항를 조회한다")
    @GetMapping(value = "notice/all")
    public List<Notice> getAllNotice(){ return noticeService.getAllNotice(); }

    @ApiOperation(value = "공지사항 조회", notes = "공지사항의 정보를 조회한다")
    @GetMapping(value = "notice/{noticeId}")
    public Notice getNotice(@PathVariable("noticeId") Long noticeId){ return noticeService.getNotice(noticeId); }

    @ApiOperation(value = "공지사항 삭제", notes = "공지사항을 삭제한다")
    @DeleteMapping(value = "notice/{noticeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotice(@PathVariable("noticeId") Long noticeId){ noticeService.deleteNoticewithId(noticeId); }

    @ApiOperation(value = "공지사항 수정 ", notes = "공지를 수정 한다.")
    @PutMapping(value = "notice")
    public void updateTask(@RequestBody NoticeUpdateParam params){
        noticeService.updateNotice(params);
    }
}
