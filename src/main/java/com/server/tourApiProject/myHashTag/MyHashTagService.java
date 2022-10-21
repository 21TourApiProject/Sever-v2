package com.server.tourApiProject.myHashTag;

import com.server.tourApiProject.hashTag.HashTag;
import com.server.tourApiProject.hashTag.HashTagRepository;
import com.server.tourApiProject.user.User;
import com.server.tourApiProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

/**
 * @className : MyHashTagService.java
 * @description : MyHashTag service 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class MyHashTagService {
    private final MyHashTagRepository myHashTagRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;

    List<Long> getMyHashTagIdList(Long userId){
        List<Long> myHashTagIdList = new ArrayList<>();
        List<MyHashTag> myHashTagList = myHashTagRepository.findByUserId(userId);
        for (MyHashTag p: myHashTagList){
            myHashTagIdList.add(p.getHashTagId());
        }return  myHashTagIdList;
    }
    /**
     * description: 사용자 선호 해시태그 조회
     *
     * @param userId - 사용자 id
     * @return 선호 해시태그 이름 list
     */
    public List<String> getMyHashTag(Long userId) {
        List<String> myHashTagNameList = new ArrayList<>();
        List<MyHashTag> myHashTagList = myHashTagRepository.findByUserId(userId);
        for(MyHashTag p : myHashTagList) {
            myHashTagNameList.add(p.getHashTagName());
        }
        return myHashTagNameList;
    }

    /**
     * description: 작성
     *
     * @param email - 사용자 email
     * @param myHashTagParams - 사용자가 선택한 선호 해시태그 list
     * @return userid
     */
    public Long createMyHashTags(String email, List<MyHashTagParams> myHashTagParams) {

        User user = userRepository.findByEmail(email);
        Long userId = user.getUserId();

        for(MyHashTagParams p : myHashTagParams) {
            MyHashTag myHashTag = new MyHashTag();
            myHashTag.setHashTagName(p.getHashTagName());
            myHashTag.setUser(user);
            myHashTag.setUserId(userId);
            HashTag hashTag = hashTagRepository.findByHashTagName(p.getHashTagName());
            myHashTag.setHashTagId(hashTag.getHashTagId());
            myHashTagRepository.save(myHashTag);
        }
        return userId;
    }

    /**
     * description: 선호 해시태그 변경
     *
     * @param userId - 사용자 id
     * @param myHashTagParams - 사용자가 선택한 선호 해시태그 list
     */
    public void changeMyHashTag(Long userId, List<MyHashTagParams> myHashTagParams) {

        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        List<MyHashTag> origin = myHashTagRepository.findByUserId(userId);
        myHashTagRepository.deleteAll(origin);
        for(MyHashTagParams p : myHashTagParams) {
            MyHashTag myHashTag = new MyHashTag();
            myHashTag.setHashTagName(p.getHashTagName());
            myHashTag.setUser(user);
            myHashTag.setUserId(userId);
            HashTag hashTag = hashTagRepository.findByHashTagName(p.getHashTagName());
            myHashTag.setHashTagId(hashTag.getHashTagId());
            myHashTagRepository.save(myHashTag);
        }
    }

    /**
     * description: 사용자의 선호 해시태그 3개 조회
     *
     * @param userId - 사용자 id
     * @return 사용자의 선호 해시태그 3개 이름 list
     */
    public List<String> getMyHashTag3(Long userId) {

        List<String> myHashTagNameList = new ArrayList<>();
        List<MyHashTag> myHashTagList = myHashTagRepository.findByUserId(userId);
        int i = 0;
        for(MyHashTag p : myHashTagList) {
            if (i > 2)
                break;
            myHashTagNameList.add(p.getHashTagName());
            i++;
        }
        return myHashTagNameList;
    }
}
