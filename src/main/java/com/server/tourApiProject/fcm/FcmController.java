package com.server.tourApiProject.fcm;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.server.tourApiProject.bigPost.postComment.PostCommentParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * className : com.server.tourApiProject.fcm description : 설명 modification : 2023-11-08(jinhyeok)
 * methodA수정 author : jinhyeok date : 2023-11-08 version : 1.0
 * <p>
 * ====개정이력(Modification Information)==== 수정일        수정자        수정내용
 * ----------------------------------------- 2023-11-08       jinhyeok       최초생성
 */
@Slf4j
@Api(tags = {"15.1 FCM"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor
public class FcmController {
    private final FcmService firebaseCloudMessageService;

    @PostMapping("message/fcm/token")
    public ResponseEntity pushMessage(@RequestBody RequestDTO requestDTO) throws IOException, FirebaseMessagingException {
        System.out.println(requestDTO.getTargetToken() + " "
            + requestDTO.getTitle() + " " + requestDTO.getBody());

        firebaseCloudMessageService.sendMessageTo(
            requestDTO.getTargetToken(),
            requestDTO.getTitle(),
            requestDTO.getBody());
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "fcm 토큰 정보 입력", notes = "fcm 토큰 정보를 입력한다")
    @PostMapping(value = "fcnToken /{userId}")
    public void createFcmToken(@PathVariable("userId") Long userId) {
        firebaseCloudMessageService.createFcmToken(userId);
    }

    @ApiOperation(value = "사용자 fcm 토큰 조회", notes = "사용자의 id로 fcm 토큰을 비교한다")
    @GetMapping(value = "fcmToken/{userId}/{fcmToken}")
    public Boolean updateFcmToken(@PathVariable("userId") Long userId, @PathVariable("fcmToken") String fcmToken){
        return firebaseCloudMessageService.updateFcmToken(userId, fcmToken); }

    @ApiOperation(value = "모든 사용자 fcm 토큰 조회", notes = "모든 사용자의 fcmtoken을 가져온다")
    @GetMapping(value = "fcmToken/all")
    public List<String> getAllFcmToken(){
        return firebaseCloudMessageService.getAllFcmToken(); }

    @ApiOperation(value = "fcmToken 삭제", notes = "사용자의 fcmToken을 삭제한다")
    @DeleteMapping(value = "fcmToken/{userId}")
    public void deleteFcmToken(@PathVariable("userId") Long userId){
        firebaseCloudMessageService.deleteFcmToken(userId);
    }
}
