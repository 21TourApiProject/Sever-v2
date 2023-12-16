package com.server.tourApiProject.user;

import com.server.tourApiProject.bigPost.post.Post;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = {"1.1 사용자"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor

/**
 * @className : UserController.java
 * @description : 사용자 controller 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "사용자정보 입력", notes = "사용자 정보를 입력한다")
    @PostMapping(value = "user")
    public String createUser(@RequestBody UserParams userParam){
        return userService.createUser(userParam);
    }

    @ApiOperation(value = "카카오 사용자정보 입력", notes = "사용자 정보를 입력한다")
    @PostMapping(value = "user/kakao")
    public String createKakaoUser(@RequestBody KakaoUserParams userParam){
        return userService.createKakaoUser(userParam);
    }

    @ApiOperation(value = "사용자정보 삭제", notes = "사용자 정보를 삭제한다")
    @DeleteMapping(value = "user/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }

    @ApiOperation(value = "사용자정보 삭제", notes = "이메일로 사용자를 찾아서 사용자 정보를 삭제한다")
    @DeleteMapping(value = "user/email/{email}")
    public void deleteUser2(@PathVariable("email") String email){
        userService.deleteUser2(email);
    }

    @ApiOperation(value = "사용자 조회", notes = "사용자 id로 사용자의 모든 정보를 조회한다")
    @GetMapping(value = "user/{userId}")
    public User getUser(@PathVariable("userId") Long userId){ return userService.getUser(userId); }

    @ApiOperation(value = "사용자 조회2", notes = "사용자 id로 사용자의 닉네임, 프로필 사진을 조회한다")
    @GetMapping(value = "user2/{userId}")
    public UserParams2 getUser2(@PathVariable("userId") Long userId){ return userService.getUser2(userId); }

    @ApiOperation(value = "사용자 로그인", notes = "사용자의 이메일과 비밀번호로 계정 여부를 확인한다")
    @GetMapping(value = "user/login/{email}/{password}")
    public Long logIn(@PathVariable("email") String email, @PathVariable("password") String password){ return userService.logIn(email, password); }

    @ApiOperation(value = "카카오 사용자 로그인", notes = "사용자의 이메일과 비밀번호로 계정 여부를 확인한다")
    @GetMapping(value = "user/kakaologin/{email}")
    public Long logIn(@PathVariable("email") String email ){ return userService.kakaoLogIn(email); }

    @ApiOperation(value = "사용자 이메일 조회", notes = "사용자의 이름, 전화번호로 이메일을 조회한다")
    @GetMapping(value = "user/login/email/{realName}/{mobilePhoneNumber}")
    public String getEmail(@PathVariable("realName") String realName, @PathVariable("mobilePhoneNumber") String mobilePhoneNumber){ return userService.getEmail(realName, mobilePhoneNumber); }

    @ApiOperation(value = "사용자 비밀번호 조회", notes = "사용자의 이메일, 이름, 전화번호로 계정 여부를 확인 후 이메일로 임시 비밀번호를 전송한다")
    @GetMapping(value = "user/login/password/{email}/{realName}/{mobilePhoneNumber}")
    public Boolean getPassword(@PathVariable("email") String email, @PathVariable("realName") String realName, @PathVariable("mobilePhoneNumber") String mobilePhoneNumber){
        return userService.getPassword(email, realName, mobilePhoneNumber);
    }

    @ApiOperation(value = "사용자 비밀번호 암호화", notes = "사용자의 비밀번호를 암호화한다. (처리 후 제거용)")
    @PostMapping(value = "user/password/encode")
    public void encodePassword(){ userService.encodePassword(); }

    @ApiOperation(value = "중복 이메일 조회", notes = "중복된 이메일이 있는지 조회한다")
    @GetMapping(value = "user/duplicate/email/{email}")
    public Boolean checkDuplicateLoginId(@PathVariable("email") String email){ return userService.checkDuplicateEmail(email); }

    @ApiOperation(value = "중복 전화번호 조회", notes = "중복된 전화번호가 있는지 조회한다")
    @GetMapping(value = "user/duplicate/mobilePhoneNumber/{mobilePhoneNumber}")
    public Boolean checkDuplicateMobilePhoneNumber(@PathVariable("mobilePhoneNumber") String mobilePhoneNumber){ return userService.checkDuplicateMobilePhoneNumber(mobilePhoneNumber); }

    @ApiOperation(value = "중복 닉네임 조회", notes = "중복된 닉네임이 있는지 조회한다")
    @GetMapping(value = "user/duplicate/nickName/{nickName}")
    public Boolean checkDuplicateNickName(@PathVariable("nickName") String nickName){ return userService.checkDuplicateNickName(nickName); }

    @ApiOperation(value = "사용자 닉네임 수정", notes = "해당 사용자의 닉네임을 수정한다")
    @PutMapping(value = "user/{userId}/nickName/{nickName}")
    public void updateNickName(@PathVariable("userId") Long userId, @PathVariable("nickName") String nickName){
        userService.changeNickName(userId, nickName);
    }

    @ApiOperation(value = "사용자 프로필 사진 수정", notes = "해당 사용자의 프로필 사진을 수정한다")
    @PutMapping(value = "user/{userId}/profileImage")
    public void updateProfileImage(@PathVariable("userId") Long userId, @RequestBody String profileImageName){
        userService.changeProfileImage(userId, profileImageName);
    }

    @ApiOperation(value = "사용자 비밀번호 수정", notes = "해당 사용자의 비밀번호를 수정한다")
    @PutMapping(value = "user/{userId}/password/{originPwd}/{newPwd}")
    public Boolean updatePassword(@PathVariable("userId") Long userId, @PathVariable("originPwd") String originPwd, @PathVariable("newPwd") String newPwd) {
        return userService.changePassword(userId, originPwd, newPwd);
    }

    @ApiOperation(value = "사용자 게시물 조회", notes = "사용자가 작성한 모든 게시물을 조회한다")
    @GetMapping(value = "user/{userId}/posts")
    public List<Post> getMyPosts(@PathVariable("userId") Long userId){ return userService.getMyPosts(userId); }

    @ApiOperation(value = "사용자가 타입 조회", notes = "사용자가 카카오 가입인지 확인한다")
    @GetMapping(value = "user/{userId}/isKakao")
    public Boolean checkIsKakao(@PathVariable("userId") Long userId){ return userService.checkIsKakao(userId); }

    @ApiOperation(value = "사용자 닉네임 조회", notes = "사용자 id로 사용자의 닉네임을 조회한다")
    @GetMapping(value = "user/{userId}/nickName")
    public Map<String, String> getNickName(@PathVariable("userId") Long userId){ return userService.getNickName(userId); }

}
