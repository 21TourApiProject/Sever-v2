package com.server.tourApiProject.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    // 중복 이메일이 있는지 조회
    User findByEmail(@Param("email") String email);

    // 중복 전화번호가 있는지 조회
    User findByMobilePhoneNumber(@Param("mobilePhoneNumber") String mobilePhoneNumber);

    // 중복 닉네임이 있는지 조회
    User findByNickName(@Param("nickName") String nickName);
}
