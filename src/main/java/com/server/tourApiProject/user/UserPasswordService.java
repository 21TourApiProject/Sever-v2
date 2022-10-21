package com.server.tourApiProject.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordService {

    /**
     * 비밀번호 암호화
     * @param passwordEncoder - 암호화 할 인코더 클래스
     * @param password - 암호화 이전 비밀번호
     * @return 변경된 유저 Entity
     */
    public String hashPassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 비밀번호 확인
     * @param passwordEncoder - 암호화 할 인코더 클래스
     * @param password - 암호화 이전 비밀번호
     * @param encryptedPassword - 암호화 이후 비밀번호
     * @return true | false
     */
    public boolean checkPassword(PasswordEncoder passwordEncoder, String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }
}
