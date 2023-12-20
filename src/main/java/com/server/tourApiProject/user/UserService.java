package com.server.tourApiProject.user;

import com.server.tourApiProject.bigPost.post.Post;
import com.server.tourApiProject.fcm.FcmService;
import com.server.tourApiProject.myWish.MyWishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

/**
 * @className : UserService.java
 * @description : 사용자 service 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-08-28     sein        주석 생성

 */
public class UserService {

    private final UserRepository userRepository;
    private final MyWishRepository myWishRepository;
    private final UserPasswordService userPasswordService;
    private final FcmService fcmService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    /**
     * description: 사용자 id로 사용자의 모든 정보 조회
     *
     * @param userId - 사용자 id
     * @return User entity
     */
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
    }

    /**
     * description: 사용자 id로 사용자의 닉네임, 프로필 사진을 조회
     *
     * @param userId - 사용자 id
     * @return User param2
     */
    public UserParams2 getUser2(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        UserParams2 userParams2 = new UserParams2();
        userParams2.setNickName(user.getNickName());
        userParams2.setProfileImage(user.getProfileImage());

        return userParams2;
    }

    /**
     * description: 사용자 생성
     *
     * @param userParam
     */
    public String createUser(UserParams userParam) {
        User user = new User();
        user.setRealName(userParam.getRealName());
        user.setSex(userParam.getSex());
        user.setBirthDay(userParam.getBirthDay());
        user.setMobilePhoneNumber(userParam.getMobilePhoneNumber());
        user.setEmail(userParam.getEmail());
//        user.setPassword(userParam.getPassword());
        user.setEncryptedPassword(userPasswordService.hashPassword(bCryptPasswordEncoder, userParam.getPassword()));

        boolean isDuplicate = true;
        while (isDuplicate) {
            String nickname = randomNickName();
            if (userRepository.findByNickName(nickname) == null) {
                isDuplicate = false;
                user.setNickName(nickname);
            }
        }
        user.setIsMarketing(userParam.getIsMarketing());
        user.setKakao(userParam.getKakao());
        user.setSignUpDt(LocalDateTime.now());

        return String.valueOf(userRepository.save(user).getUserId());
    }

    /**
     * description: 랜덤 닉네임 생성
     *
     * @return nickname
     */
    private String randomNickName() {
        String[] front = {"별헤는", "별난", "별이좋은", "별을찾는", "별보는", "반짝이는", "비몽사몽", "여행하는", "우주속의", "졸린", "별그리는", "반짝반짝", "하품하는", "코고는",
                "여행중인", "별에서온", "밤하늘의", "여름밤의", "겨울밤의", "별빛속의", "나른한", "별똥별과", "꾸벅꾸벅", "야행성", "배낭을멘", "달빛속의", "새벽감성", "은하수속",
                "자유로운", "캠핑하는", "낭만적인", "느낌있는", "은하수와", "옥탑방", "꿈속의", "잠든", "감성적인", "잠오는", "설레는", "행복한", "로맨틱한", "감미로운", "신비로운", "꿈꾸는", "새벽녘의"};
        String[] back = {"너구리", "뱁새", "호랑이", "햄스터", "쿼카", "미어캣", "반달곰", "칡", "고영이", "타조", "낙타", "라쿤", "북극곰", "막대사탕", "보드카", "위스키", "막걸리",
                "영혼", "꼬마유령", "대학원생", "돌하르방", "마법사", "하모니카", "도깨비", "반딧불이", "멍뭉이", "호롱불", "사막여우", "고슴도치", "다람쥐", "수달", "천문학자", "별사탕",
                "모닥불", "히치하이커", "벽난로", "기타리스트", "여행작가", "몽상가", "음유시인", "고래", "올빼미"};
        Random random = new Random();
        int f = random.nextInt(45);
        int b = random.nextInt(42);
        int n = random.nextInt(1000);
        return front[f] + " " + back[b] + " " + n;
    }

    public String createKakaoUser(KakaoUserParams userParam) {
        User user = new User();
        user.setEmail(userParam.getEmail());
        user.setNickName(userParam.getNickName());
        user.setProfileImage(userParam.getProfileImage());
        user.setSignUpDt(LocalDateTime.now());
        user.setKakao(true);
        if (userParam.getMobilePhoneNumber() != null)
            user.setMobilePhoneNumber(userParam.getMobilePhoneNumber());
        if (userParam.getSex() != null)
            user.setSex(userParam.getSex());
        if (userParam.getBirthDay() != null)
            user.setBirthDay(userParam.getBirthDay());
        if (userParam.getAgeRange() != null)
            user.setAgeRange(userParam.getAgeRange());

        return String.valueOf(userRepository.save(user).getUserId());
    }

    /**
     * description: 이메일 중복 체크
     *
     * @param email - 사용자가 요청한 이메일
     * @return true - 중복 아님, false - 중복
     */
    public Boolean checkDuplicateEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user == null;
    }

    /**
     * description: 전화번호 중복 체크
     *
     * @param mobilePhoneNumber - 사용자가 요청한 전화번호
     * @return true - 중복 아님, false - 중복
     */
    public Boolean checkDuplicateMobilePhoneNumber(String mobilePhoneNumber) {
        User user = userRepository.findByMobilePhoneNumber(mobilePhoneNumber);
        return user == null;
    }

    /**
     * description: 닉네임 중복 체크
     *
     * @param nickName - 사용자가 요청한 닉네임
     * @return true - 중복 아님, false - 중복
     */
    public Boolean checkDuplicateNickName(String nickName) {
        User user = userRepository.findByNickName(nickName);
        return user == null;
    }

    /**
     * description: 사용자의 이메일과 비밀번호로 계정 여부를 확인
     *
     * @param email    - 이메일
     * @param password - 비밀번호
     * @return long (-1 - 계정 없음, -2 - 카카오 로그인 계정, 나머지 - userid)
     */
    public Long logIn(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return -1L;
        }
        if (user.getKakao()) {
            return -2L;
        }
        if (user.getEncryptedPassword() == null && user.getPassword().equals(password)) { //비밀번호 암호화가 완료되면 지우기
            return user.getUserId();
        }
        if (userPasswordService.checkPassword(bCryptPasswordEncoder, password, user.getEncryptedPassword())) {
            return user.getUserId();
        }
        return -1L;
    }

    public Long kakaoLogIn(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return -1L;
        }
        return user.getUserId();
    }

    /**
     * description: 사용자의 이름, 전화번호로 이메일 조회
     *
     * @param realName          - 이름
     * @param mobilePhoneNumber - 전화번호
     * @return string ("none" - 해당 사용자 없음, 나머지 - 이메일)
     */
    public String getEmail(String realName, String mobilePhoneNumber) {
        User user = userRepository.findByMobilePhoneNumber(mobilePhoneNumber);
        if (user == null) {
            return "none";
        }
        if (user.getRealName().equals(realName)) {
            return user.getEmail();
        }
        return "none";
    }

    /**
     * description: 비밀번호 찾기
     *
     * @param email             - 이메일
     * @param realName          - 이름
     * @param mobilePhoneNumber - 전화번호
     * @return Boolean (true - 성공, false - 실패)
     */
    public Boolean getPassword(String email, String realName, String mobilePhoneNumber) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getRealName().equals(realName) || !user.getMobilePhoneNumber().equals(mobilePhoneNumber)) {
            return false;
        }

        String tmpPassword = getTmpPassword(8);
        user.setEncryptedPassword(userPasswordService.hashPassword(bCryptPasswordEncoder, tmpPassword));
        userRepository.save(user);
        return sendTmpPassword(email, tmpPassword);
    }

    /**
     * description : 사용자 이메일로 임시 비밀번호 전송
     *
     * @param email       - 이메일
     * @param tmpPassword - 임시 비밀번호
     * @return Boolean (true - 전송 성공, false - 전송 실패)
     */
    public Boolean sendTmpPassword(String email, String tmpPassword) {

        String fromMail = "starsufers@gmail.com";
        String title = "[별헤는밤] 임시 비밀번호 입니다.";
        String content = "임시 비밀번호: " + tmpPassword +
                "\n\n위 임시 비밀번호로 로그인 후, 마이페이지에서 비밀번호를 변경해주세요.\n";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setFrom(fromMail);
            message.setSubject(title);
            message.setText(content);
            javaMailSender.send(message);
            return true;
        } catch (MailException e) {
            log.error("임시 비밀번호 전송 오류 " + e.getMessage());
            return false;
        }

    }

    /**
     * description : 임시 비밀번호 생성
     *
     * @param size - 임시 비밀번호 길이
     * @return String 임시 비밀번호
     */
    public String getTmpPassword(int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }

    /**
     * description: 사용자의 닉네임 수정
     *
     * @param userId   - 사용자 id
     * @param nickName - 사용자 닉네임
     */
    public void changeNickName(Long userId, String nickName) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        user.setNickName(nickName);
        userRepository.save(user);
    }

    /**
     * description: 사용자의 프로필 사진 수정
     *
     * @param userId           - 사용자 id
     * @param profileImageName - 사용자 프로필 이미지
     */
    public void changeProfileImage(Long userId, String profileImageName) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        user.setProfileImage(profileImageName);
        userRepository.save(user);
    }

    /**
     * description: 사용자의 비밀번호 수정
     *
     * @param userId    - 사용자 id
     * @param originPwd - 기존 비밀번호
     * @param newPwd    - 새로운 비밀번호
     * @return boolean (true - 비밀번호 정상 변경, false - 기존 비밀번호 맞지 않음)
     */
    public Boolean changePassword(Long userId, String originPwd, String newPwd) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
//        if (!user.getPassword().equals(originPwd)){
        if (!userPasswordService.checkPassword(bCryptPasswordEncoder, originPwd, user.getEncryptedPassword())) {
            return false;
        }
//        user.setPassword(newPwd);
        user.setEncryptedPassword(userPasswordService.hashPassword(bCryptPasswordEncoder, newPwd));
        userRepository.save(user);
        return true;
    }

    /**
     * description: 사용자가 작성한 모든 게시물 조회
     *
     * @param userId - 사용자 id
     * @return 게시물 list
     */
    public List<Post> getMyPosts(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        List<Post> myPosts = user.getMyPosts();
        return myPosts;
    }

    /**
     * description: 사용자 정보 삭제
     *
     * @param userId - 사용자 id
     */
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        List<Post> myPosts = user.getMyPosts();
        for(Post post: myPosts){
            myWishRepository.deleteByItemIdAndWishType(post.getPostId(),2);
        }
        userRepository.deleteById(userId);

    }

    /**
     * description: 사용자 이메일로 사용자 정보 삭제
     *
     * @param email - 사용자 이메일
     */
    public void deleteUser2(String email) {
        User user = userRepository.findByEmail(email);
        userRepository.delete(user);
    }

    public boolean checkIsKakao(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        return user.getKakao();
    }

    /**
     * description : 암호화되지 않은 사용자의 비밀번호를 암호화한다.
     */
    public void encodePassword() {
        List<User> all = userRepository.findAll();
        for (User user : all) {
            if (user.getPassword() != null && user.getEncryptedPassword() == null) {
                user.setEncryptedPassword(userPasswordService.hashPassword(bCryptPasswordEncoder, user.getPassword()));
                userRepository.save(user);
            }
        }
    }

    public Map<String, String> getNickName(Long userId) {
        return Map.of("nickName", userRepository.findById(userId).get().getNickName());
    }
}
