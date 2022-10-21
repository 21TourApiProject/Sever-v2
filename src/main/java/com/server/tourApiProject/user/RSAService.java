package com.server.tourApiProject.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author : sein
 * @version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2022-09-12     sein        주석 생성
 * @className : RSAService.java
 * @description : 사용자 비밀번호 암호화 service 입니다.
 * @modification : 2022-09-12(sein) 수정
 * @date : 2022-09-12
 */

@Service
public class RSAService {

    private final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4veKCtvjMbdACgTDS0EXveDgVHSndOkJWj6Qt1Bq6g4z8SVFraQ0LADFeosvaaasZgVs2ny5cLmAG1HmiHvU4HJrcsPb4jxUEm+VUJCNHxb6G4BQ11sJlZn3hyphfWApaSzKodzoTz0qgR4XMkoZYqgyxP7wABuDPLQLTnr44PQIDAQAB";

//    @Value("${rsa.private-key}")
    private String PRIVATE_KEY;

    public String getPRIVATE_KEY(){
        return PRIVATE_KEY;
    }

    /**
     * @param plainData - 비밀번호
     * @return 암호화한 비밀번호
     * @description : RSA로 암호화
     */
    public String encode(String plainData) {
        String encryptedData = null;
        try {
            //평문으로 전달받은 공개키를 공개키객체로 만드는 과정
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] bytePublicKey = Base64.getDecoder().decode(PUBLIC_KEY.getBytes());
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            //만들어진 공개키객체를 기반으로 암호화모드로 설정하는 과정
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            //평문을 암호화하는 과정
            byte[] byteEncryptedData = cipher.doFinal(plainData.getBytes());
            encryptedData = Base64.getEncoder().encodeToString(byteEncryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedData;
    }


    /**
     * @param encryptedData - 암호화된 비밀번호
     * @return 복호화한 비밀번호
     * @description :  입니다.
     */
    public String decode(String encryptedData) {
        String decryptedData = null;
        try {
            //평문으로 전달받은 개인키를 개인키객체로 만드는 과정
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] bytePrivateKey = Base64.getDecoder().decode(PRIVATE_KEY.getBytes());
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            //만들어진 개인키객체를 기반으로 암호화모드로 설정하는 과정
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            //암호문을 평문화하는 과정
            byte[] byteEncryptedData = Base64.getDecoder().decode(encryptedData.getBytes());
            byte[] byteDecryptedData = cipher.doFinal(byteEncryptedData);
            decryptedData = new String(byteDecryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedData;
    }
}
