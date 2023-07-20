package com.server.tourApiProject.myWish;

import com.server.tourApiProject.bigPost.post.Post;
import com.server.tourApiProject.bigPost.post.PostRepository;
import com.server.tourApiProject.bigPost.postHashTag.PostHashTag;
import com.server.tourApiProject.bigPost.postHashTag.PostHashTagRepository;
import com.server.tourApiProject.bigPost.postImage.PostImage;
import com.server.tourApiProject.bigPost.postImage.PostImageRepository;
import com.server.tourApiProject.observation.Observation;
import com.server.tourApiProject.observation.ObservationRepository;
import com.server.tourApiProject.observation.observeHashTag.ObserveHashTag;
import com.server.tourApiProject.observation.observeHashTag.ObserveHashTagRepository;
import com.server.tourApiProject.observation.observeImage.ObserveImage;
import com.server.tourApiProject.observation.observeImage.ObserveImageRepository;
import com.server.tourApiProject.touristPoint.contentType.ContentType;
import com.server.tourApiProject.touristPoint.contentType.ContentTypeRepository;
import com.server.tourApiProject.touristPoint.touristData.TouristData;
import com.server.tourApiProject.touristPoint.touristData.TouristDataRepository;
import com.server.tourApiProject.touristPoint.touristDataHashTag.TouristDataHashTag;
import com.server.tourApiProject.touristPoint.touristDataHashTag.TouristDataHashTagRepository;
import com.server.tourApiProject.user.User;
import com.server.tourApiProject.user.UserRepository;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type My wish service.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

/**
 * @className : MyWishService.java
 * @description : MyWish service 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성
        2023-04-20      gyulchyoung     메소드 추가

 */
public class MyWishService {

    private final String TAG = "MyWishService";

    private final MyWishRepository myWishRepository;
    private final ContentTypeRepository contentTypeRepository;
    private final UserRepository userRepository;
    private final ObservationRepository observationRepository;
    private final ObserveImageRepository observeImageRepository;
    private final ObserveHashTagRepository observeHashTagRepository;
    private final TouristDataRepository touristDataRepository;
    private final TouristDataHashTagRepository touristDataHashTagRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostHashTagRepository postHashTagRepository;

    /**
     * description: 사용자 찜 추가
     *
     * @param userId   - 사용자 id
     * @param itemId   - 컨텐츠 id
     * @param wishType - 컨텐츠 타입 (0 - 관측지, 1 - 관광지, 2 - 게시물)
     */
    @Transactional
    public void createMyWish(Long userId, Long itemId, Integer wishType) {

        MyWish myWish = new MyWish();

        switch(wishType) {
            case 0: //관측지
                Observation observation = observationRepository.findById(itemId).orElseThrow(IllegalAccessError::new);  //itemId에 해당하는 관측지 id가 없으면 오류 발생
                myWish.setUserId(userId);
                myWish.setUser(userRepository.findById(userId).orElseThrow(IllegalAccessError::new));
                myWish.setWishType(0); // 관측지
                myWish.setItemId(itemId);
                String now0 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                myWish.setWishTime(Long.parseLong(now0));
                myWishRepository.save(myWish);
                observation.setSaved(observation.getSaved()+1);
                break;
            case 1: //관광지
                touristDataRepository.findById(itemId).orElseThrow(IllegalAccessError::new);  //itemId에 해당하는 관광지 id가 없으면 오류 발생
                myWish.setUserId(userId);
                myWish.setUserId(userId);
                myWish.setUser(userRepository.findById(userId).orElseThrow(IllegalAccessError::new));
                myWish.setWishType(1); // 관광지
                myWish.setItemId(itemId);
                String now1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                myWish.setWishTime(Long.parseLong(now1));
                myWishRepository.save(myWish);
                break;
            case 2: //게시물
                Post post = postRepository.findById(itemId).orElseThrow(IllegalAccessError::new);  //itemId에 해당하는 게시물 id가 없으면 오류 발생
                myWish.setUserId(userId);
                myWish.setUser(userRepository.findById(userId).orElseThrow(IllegalAccessError::new));
                myWish.setWishType(2); // 게시물
                myWish.setItemId(itemId);
                String now2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                myWish.setWishTime(Long.parseLong(now2));
                myWishRepository.save(myWish);
                if(post.getSaved()==null){
                    post.setSaved(1L);
                }else{
                    post.setSaved(post.getSaved()+1);
                }
                break;
        }
    }

    /**
     * description: 해당 컨텐츠가 찜한 상태인지 확인
     *
     * @param userId   - 사용자 id
     * @param itemId   - 컨텐츠 id
     * @param wishType - 컨텐츠 타입 (0 - 관측지, 1 - 관광지, 2 - 게시물)
     * @return true - 이미 찜 되어 있음 / false - 찜 되어 있지 않음
     */
    public Boolean isThereMyWish(Long userId, Long itemId, Integer wishType) {

        Optional<MyWish> myWish = myWishRepository.findByUserIdAndItemIdAndWishType(userId, itemId, wishType);
        return myWish.isPresent();
    }

    /**
     * description: 찜 제거
     *
     * @param userId   - 사용자 id
     * @param itemId   - 컨텐츠 id
     * @param wishType - 컨텐츠 타입 (0 - 관측지, 1 - 관광지, 2 - 게시물)
     */
    @Transactional
    public void deleteMyWish(Long userId, Long itemId, Integer wishType) {

        Optional<MyWish> myWishOp = myWishRepository.findByUserIdAndItemIdAndWishType(userId, itemId, wishType);
        MyWish myWish = myWishOp.get();
        myWishRepository.delete(myWish);
        switch(wishType) {
            case 0: //관측지
                Observation observation = observationRepository.findById(itemId).orElseThrow(IllegalAccessError::new);  //itemId에 해당하는 관측지 id가 없으면 오류 발생
                observation.setSaved(observation.getSaved()-1);
                break;
            case 1: //관광지
                break;
            case 2: //게시물
                Post post = postRepository.findById(itemId).orElseThrow(IllegalAccessError::new);  //itemId에 해당하는 게시물 id가 없으면 오류 발생
                post.setSaved(post.getSaved()-1);
                break;
        }
    }

    /**
     * description: 사용자가 찜한 관측지 목록 조회
     *
     * @param userId - 사용자 id
     * @return 찜한 관측지 목록
     */
    public List<MyWishParams01> getMyWishObservation(Long userId) {

        List<MyWishParams01> result= new ArrayList<>();
        List<MyWish> wish = myWishRepository.findByUserIdAndWishType(userId, 0);
        for (MyWish myWish: wish){
            Long observationId = myWish.getItemId();
            Observation observation = observationRepository.findById(observationId).orElseThrow(IllegalAccessError::new);
            MyWishParams01 myWishParams01 = new MyWishParams01();
            myWishParams01.setItemId(observationId);

            List<ObserveImage> imageList = observeImageRepository.findByObservationId(observationId);
            if (!imageList.isEmpty()){
                ObserveImage observeImage = imageList.get(0);
                myWishParams01.setThumbnail(observeImage.getImage());
            } else {
                myWishParams01.setThumbnail(null);
            }

            myWishParams01.setTitle(observation.getObservationName());
            myWishParams01.setAddress(observation.getAddress());
            myWishParams01.setCat3Name(observation.getObserveType());
            myWishParams01.setOverviewSim(observation.getOutline().substring(0,15) + "...");

            List<ObserveHashTag> hashTagList = observeHashTagRepository.findByObservationId(observationId);
            List<String> hashTagNames = new ArrayList<>();
            int i = 0;
            for (ObserveHashTag hashTag : hashTagList){
                if (i > 2)
                    break;
                hashTagNames.add(hashTag.getHashTagName());
                i++;
            }
            myWishParams01.setHashTagNames(hashTagNames);

            result.add(myWishParams01);
        }
        return result;
    }

    /**
     * description: 사용자가 찜한 관광지 목록 조회
     *
     * @param userId - 사용자 id
     * @return 찜한 관광지 목록
     */
    public List<MyWishParams01> getMyWishTouristPoint(Long userId) {
        List<MyWishParams01> result= new ArrayList<>();
        List<MyWish> wish = myWishRepository.findByUserIdAndWishType(userId, 1);
        for (MyWish myWish: wish){
            Long contentId = myWish.getItemId();
            TouristData touristData = touristDataRepository.findById(contentId).orElseThrow(IllegalAccessError::new);
            MyWishParams01 myWishParams01 = new MyWishParams01();
            myWishParams01.setItemId(contentId);
            myWishParams01.setThumbnail(touristData.getFirstImage());
            myWishParams01.setTitle(touristData.getTitle());
            myWishParams01.setAddress(touristData.getAddr());
            myWishParams01.setCat3Name(contentTypeRepository.findByCat3Code(touristData.getCat3()).getCat3Name());
            myWishParams01.setOverviewSim(touristData.getOverviewSim());

            List<TouristDataHashTag> hashTagList = touristDataHashTagRepository.findByContentId(contentId);
            List<String> hashTagNames = new ArrayList<>();
            int i = 0;
            for (TouristDataHashTag hashTag : hashTagList){
                if(i > 2)
                    break;
                hashTagNames.add(hashTag.getHashTagName());
                i++;
            }
            myWishParams01.setHashTagNames(hashTagNames);

            result.add(myWishParams01);
        }
        return result;
    }

    /**
     * description: 사용자가 찜한 게시물 목록 조회
     *
     * @param userId - 사용자 id
     * @return 찜한 게시물 목록
     */
    public List<MyWishParams2> getMyWishPost(Long userId) {
        List<MyWishParams2> result= new ArrayList<>();
        List<MyWish> wish = myWishRepository.findByUserIdAndWishType(userId, 2);
        for (MyWish myWish: wish){
            Long postId = myWish.getItemId();
            Post post = postRepository.findById(postId).orElseThrow(IllegalAccessError::new);
            MyWishParams2 myWishParams2 = new MyWishParams2();
            myWishParams2.setItemId(postId);

            List<PostImage> imageList = postImageRepository.findByPostId(postId);
            if (!imageList.isEmpty()){
                PostImage postImage = imageList.get(0);
                myWishParams2.setThumbnail(postImage.getImageName());
            } else {
                myWishParams2.setThumbnail(null);
            }

            myWishParams2.setTitle(post.getPostTitle());

            myWishParams2.setNickName(post.getUser().getNickName());
            myWishParams2.setProfileImage(post.getUser().getProfileImage());

            List<PostHashTag> hashTagList = postHashTagRepository.findByPostId(postId);
            int i = 0;
            List<String> hashTagNames = new ArrayList<>();
            for (PostHashTag hashTag : hashTagList){
                if(i > 2)
                    break;
                hashTagNames.add(hashTag.getHashTagName());
                i++;
            }
            myWishParams2.setHashTagNames(hashTagNames);

            result.add(myWishParams2);
        }
        return result;
    }

    /**
     * description: 사용자가 최근에 찜한 컨텐츠 3개 조회
     *
     * @param userId - 사용자 id
     * @return 최근에 찜한 컨텐츠 3개 list
     */
    public List<MyWishParams3> getMyWish3(Long userId) {
        List<MyWishParams3> result = new ArrayList<>();
        List<MyWish> list = myWishRepository.findByUserId(userId);
        List<MyWish> three = new ArrayList<>();
        int len = list.size();
        if (len > 3){
            for (int i=len-1; i>len-4; i--){
                three.add(list.get(i));
            }
        } else {
            three = list;
        }

        for (MyWish myWish : three){
            MyWishParams3 myWishParams3 = new MyWishParams3();
            if (myWish.getWishType() == 0){
                myWishParams3.setWishType(0);
                Observation observation = observationRepository.findById(myWish.getItemId()).orElseThrow(IllegalAccessError::new);
                myWishParams3.setTitle(observation.getObservationName());
                List<ObserveImage> imageList = observeImageRepository.findByObservationId(myWish.getItemId());
                if (!imageList.isEmpty()) {
                    ObserveImage observeImage = imageList.get(0);
                    myWishParams3.setThumbnail(observeImage.getImage());
                } else{
                    myWishParams3.setThumbnail(null);
                }

                result.add(myWishParams3);
            }
            else if (myWish.getWishType() == 1){
                myWishParams3.setWishType(1);
                TouristData touristData = touristDataRepository.findById(myWish.getItemId()).orElseThrow(IllegalAccessError::new);
                myWishParams3.setTitle(touristData.getTitle());
                myWishParams3.setThumbnail(touristData.getFirstImage());

                result.add(myWishParams3);
            }
            else if (myWish.getWishType() == 2){
                myWishParams3.setWishType(2);
                Post post = postRepository.findById(myWish.getItemId()).orElseThrow(IllegalAccessError::new);
                myWishParams3.setTitle(post.getPostTitle());
                List<PostImage> imageList = postImageRepository.findByPostId(myWish.getItemId());
                if (!imageList.isEmpty()) {
                    PostImage postImage = imageList.get(0);
                    myWishParams3.setThumbnail(postImage.getImageName());
                } else{
                    myWishParams3.setThumbnail(null);
                }

                result.add(myWishParams3);
            }

        }
        return result;
    }

    /**
     * TODO 좋아요 개수 저장용 메소드.
     * @param  -
     * @return void
     * @throws
     */
    public Integer updateSavedCount(){

        List<WishCountParams.WishCount> wishParams = myWishRepository.findWishCount();

        if(!wishParams.isEmpty()) {
            log.info(TAG, wishParams.size());
            for (WishCountParams.WishCount w : wishParams) {

                if (w.getWishType() == 0) {
                    Observation obs = observationRepository.getById(w.getItemId());
                    obs.setSaved(w.getCount());
                    observationRepository.save(obs);

                } else if (w.getWishType() == 2) {
                    Post p = postRepository.getById(w.getItemId());
                    p.setSaved(w.getCount());
                    postRepository.save(p);
                } else {
                    log.info("at updateSavedCount, wrong result");
                }
            }
        }else {
            log.error("at updateSavedCount, no result");
        }
        return wishParams.size();

    }
    
    

}
