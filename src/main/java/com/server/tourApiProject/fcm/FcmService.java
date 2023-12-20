package com.server.tourApiProject.fcm;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import com.server.tourApiProject.user.User;
import com.server.tourApiProject.user.UserRepository;
import com.server.tourApiProject.user.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * className : com.server.tourApiProject.fcm description : 설명 modification : 2023-11-08(jinhyeok)
 * methodA수정 author : jinhyeok date : 2023-11-08 version : 1.0
 * <p>
 * ====개정이력(Modification Information)==== 수정일        수정자        수정내용
 * ----------------------------------------- 2023-11-08       jinhyeok       최초생성
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

public class FcmService {

    private  final FirebaseMessaging firebaseMessaging;
    private final FcmTokenRepository fcmTokenRepository;
    private final UserRepository userRepository;

    public void sendMessageAll(List<String> targetTokenList, String title, String body) throws  InterruptedException{
//        Notification notification = Notification.builder()
//            .setTitle(title)
//            .setBody(body)
//            .build();

        List<Message> messages = targetTokenList.stream().map(token->Message.builder()
            .setToken(token)
            .putData("click","alarm")
            .putData("title",title)
            .putData("body",body)
            .putData("isNotice","notification")
            .build()).collect(Collectors.toList());
        BatchResponse response;
        try{
            response =firebaseMessaging.sendAll(messages);
            if(response.getFailureCount()>0){ //실패한 토큰들 확인하기
                List<SendResponse>responses = response.getResponses();
                List<String> failedTokens =new ArrayList<>();
                for(int i=0;i<responses.size();i++){
                    failedTokens.add(targetTokenList.get(i));
                }
            }
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageTo(String targetToken, String title ,String body) throws  FirebaseMessagingException{
//        Notification notification = Notification.builder()
//            .setTitle(title)
//            .setBody(body)
//            .build();
        Message message = Message.builder()
            .setToken(targetToken)
            .putData("click","alarm")
            .putData("title",title)
            .putData("body",body)
            .putData("isNotice","comment")
            .build();
        try{
            firebaseMessaging.send(message);
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
        }
    }

    public void createFcmToken(Long userId){
        FcmToken fcmToken = new FcmToken();
        fcmToken.setUser(userRepository.findById(userId).orElseThrow(IllegalAccessError::new));
        fcmToken.setUserId(userId);
        fcmToken.setFcmToken(null);
        fcmTokenRepository.save(fcmToken);
    }

    /**
     * description: 사용자 id로 fcm token 찾기
     *
     * @param userId          - 이름
     * @return Boolean (true - 토큰 동일함, false - 토큰 업데이트)
     */
    public Boolean updateFcmToken(Long userId,String fcmToken) {
        List<FcmToken> tokenList = fcmTokenRepository.findByUserId(userId);
        if(tokenList.isEmpty()){
            FcmToken nToken = new FcmToken();
            nToken.setFcmToken(fcmToken);
            nToken.setUserId(userId);
            nToken.setUser(userRepository.findById(userId).orElseThrow(IllegalAccessError::new));
            fcmTokenRepository.save(nToken);
            return false;
        }else{
            FcmToken token = tokenList.get(0);
            if(token.getFcmToken() == null ||!token.getFcmToken().equals(fcmToken)){
                token.setFcmToken(fcmToken);
                return false;
            }
            else {
                return true;
            }
        }
    }

    /**
     * description: 사용자 id로 fcm token 찾기
     *
     * @return List<String> (토큰을 가지고 있는 모든 유저 - 로그아웃한 유저는 토큰이 삭제됨)
     */
    public List<String> getAllFcmToken() {
        List<String> result= new ArrayList<>();
        List<FcmToken> fcmTokenList = fcmTokenRepository.findAll();
        for(FcmToken token: fcmTokenList){
            if(token.getFcmToken()!=null){
                result.add(token.getFcmToken());
            }
        }
        return result;
    }
    /**
     * description: fcmToken 삭제하는 메소드.
     */
    public void deleteFcmToken(Long userId){
        List<FcmToken> fcmTokenList = fcmTokenRepository.findByUserId(userId);

        fcmTokenRepository.deleteById(fcmTokenList.get(0).getFcmTokenId());}

}
