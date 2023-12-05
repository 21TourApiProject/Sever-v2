package com.server.tourApiProject.fcm;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * className : com.server.tourApiProject.fcm description : 설명 modification : 2023-11-08(jinhyeok)
 * methodA수정 author : jinhyeok date : 2023-11-08 version : 1.0
 * <p>
 * ====개정이력(Modification Information)==== 수정일        수정자        수정내용
 * ----------------------------------------- 2023-11-08       jinhyeok       최초생성
 */

@AllArgsConstructor
@Data
public class RequestDTO {
    private String title;
    private String body;
    private String targetToken;
}