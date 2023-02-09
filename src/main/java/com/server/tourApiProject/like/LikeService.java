package com.server.tourApiProject.like;

import com.server.tourApiProject.bigPost.post.PostRepository;
import com.server.tourApiProject.bigPost.postHashTag.PostHashTagRepository;
import com.server.tourApiProject.bigPost.postImage.PostImageRepository;
import com.server.tourApiProject.myWish.MyWish;
import com.server.tourApiProject.myWish.MyWishRepository;
import com.server.tourApiProject.observation.ObservationRepository;
import com.server.tourApiProject.observation.observeHashTag.ObserveHashTagRepository;
import com.server.tourApiProject.observation.observeImage.ObserveImageRepository;
import com.server.tourApiProject.touristPoint.contentType.ContentTypeRepository;
import com.server.tourApiProject.touristPoint.touristData.TouristDataRepository;
import com.server.tourApiProject.touristPoint.touristDataHashTag.TouristDataHashTagRepository;
import com.server.tourApiProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

/**
 * className : com.server.tourApiProject.like
 * description : 게시글 좋아요 Service
 * modification : 2023-02-08(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-02-08
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-02-08       jinhyeok       최초생성
 */
public class LikeService {

    private final LikeRepository likeRepository;
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
     * description: 사용자 좋아요 추가
     *
     * @param userId - 사용자 id
     * @param itemId - 컨텐츠 id
     * @param likeType - 컨텐츠 타입 (0 - 관측지, 1 - 관광지, 2 - 게시물)
     */
    public void createLike(Long userId, Long itemId, Integer likeType) {

        Like like = new Like();

        switch(likeType) {
            case 0: //관측지
                observationRepository.findById(itemId).orElseThrow(IllegalAccessError::new);  //itemId에 해당하는 관측지 id가 없으면 오류 발생
                like.setUserId(userId);
                like.setUser(userRepository.findById(userId).orElseThrow(IllegalAccessError::new));
                like.setLikeType(0); // 관측지
                like.setItemId(itemId);
                String now0 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                like.setLikeTime(Long.parseLong(now0));
                likeRepository.save(like);
                break;
            case 1: //관광지
                touristDataRepository.findById(itemId).orElseThrow(IllegalAccessError::new);  //itemId에 해당하는 관광지 id가 없으면 오류 발생
                like.setUserId(userId);
                like.setUserId(userId);
                like.setUser(userRepository.findById(userId).orElseThrow(IllegalAccessError::new));
                like.setLikeType(1); // 관광지
                like.setItemId(itemId);
                String now1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                like.setLikeTime(Long.parseLong(now1));
                likeRepository.save(like);
                break;
            case 2: //게시물
                postRepository.findById(itemId).orElseThrow(IllegalAccessError::new);  //itemId에 해당하는 게시물 id가 없으면 오류 발생
                like.setUserId(userId);
                like.setUser(userRepository.findById(userId).orElseThrow(IllegalAccessError::new));
                like.setLikeType(2); // 게시물
                like.setItemId(itemId);
                String now2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                like.setLikeTime(Long.parseLong(now2));
                likeRepository.save(like);
                break;
        }
    }

    /**
     * description: 해당 컨텐츠가 좋아요를 누른 상태인지 확인
     *
     * @param userId - 사용자 id
     * @param itemId - 컨텐츠 id
     * @param likeType - 컨텐츠 타입 (0 - 관측지, 1 - 관광지, 2 - 게시물)
     * @return true - 이미 찜 되어 있음 / false - 찜 되어 있지 않음
     */
    public Boolean isThereLike(Long userId, Long itemId, Integer likeType) {

        Optional<Like> like = likeRepository.findByUserIdAndItemIdAndLikeType(userId, itemId, likeType);
        return like.isPresent();
    }

    /**
     * description: 좋아요 제거
     *
     * @param userId - 사용자 id
     * @param itemId - 컨텐츠 id
     * @param likeType - 컨텐츠 타입 (0 - 관측지, 1 - 관광지, 2 - 게시물)
     */
    public void deleteLike(Long userId, Long itemId, Integer likeType) {

        Optional<Like> likeOp = likeRepository.findByUserIdAndItemIdAndLikeType(userId, itemId, likeType);
        Like like =likeOp.get();
        likeRepository.delete(like);
    }

    /**
     * description: 좋아요 수 가져오기
     *
     * @param itemId - 컨텐츠 id
     * @param likeType - 컨텐츠 타입 (0 - 관측지, 1 - 관광지, 2 - 게시물)
     */
    public LikeParams getLikeCount(Long itemId, Integer likeType){

        LikeParams likeParams = new LikeParams();
        List<Like> likeList = likeRepository.findByItemIdAndLikeType(itemId,likeType);
        likeParams.setLikeCount(likeList.size());
        likeParams.setItemid(itemId);
        return likeParams;
    }
}
