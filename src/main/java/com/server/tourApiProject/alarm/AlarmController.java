package com.server.tourApiProject.alarm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"9.1 알림"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
/**
* @className : AlarmController.java
* @description : 알림 게시판 controller 입니다.
* @modification : 2022-08-10(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-10
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-10       jinhyeok       주석 수정

 */
public class AlarmController {
    private final AlarmService alarmService;

    @ApiOperation(value = "모든 알림 조회", notes = "모든 알림를 조회한다")
    @GetMapping(value = "alarms/")
    public List<Alarm> getAllAlarm(){ return alarmService.getAllAlarm(); }

    @ApiOperation(value = "알림 입력", notes = "알림 정보를 입력한다")
    @PostMapping(value = "alarm/")
    public void createAlarm(@RequestBody Alarm alarm){
        alarmService.createAlarm(alarm);
    }
}
