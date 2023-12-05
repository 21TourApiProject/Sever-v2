package com.server.tourApiProject.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * className : com.server.tourApiProject.common.config description : 설명 modification :
 * 2023-11-27(jinhyeok) methodA수정 author : jinhyeok date : 2023-11-27 version : 1.0
 * <p>
 * ====개정이력(Modification Information)==== 수정일        수정자        수정내용
 * ----------------------------------------- 2023-11-27       jinhyeok       최초생성
 */
@Configuration
public class FcmConfig {
    @Bean
    FirebaseMessaging firebaseMessaging()throws IOException{
        ClassPathResource resource = new ClassPathResource("tour-api-project-firebase-adminsdk-uc1bp-c0d9ab355b.json");

        InputStream refreshToken = resource.getInputStream();

        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

        if(firebaseAppList!=null && !firebaseAppList.isEmpty()){
            for(FirebaseApp app :firebaseAppList){
                if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
                    firebaseApp = app;
                }
            }
        }else{
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken)).build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
