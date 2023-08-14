package com.server.tourApiProject.bigPost.post;

import com.server.tourApiProject.bigPost.postHashTag.PostHashTag;
import com.server.tourApiProject.bigPost.postHashTag.PostHashTagRepository;
import com.server.tourApiProject.bigPost.postImage.PostImage;
import com.server.tourApiProject.bigPost.postImage.PostImageRepository;
import com.server.tourApiProject.myWish.MyWishRepository;
import com.server.tourApiProject.observation.Observation;
import com.server.tourApiProject.observation.ObservationRepository;
import com.server.tourApiProject.search.Filter;
import com.server.tourApiProject.search.SearchParams1;
import com.server.tourApiProject.user.User;
import com.server.tourApiProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

/**
* @className : PostService.java
* @description : 게시물 Service 입니다.(게시물 이미지,해시태그 가져오기, 클릭 시 기능, 게시물 삭제 등 구현)
* @modification : 2022-08-05(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-05
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-05       jinhyeok       주석 수정

 */
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ObservationRepository observationRepository;
    private final PostImageRepository postImageRepository;
    private final PostHashTagRepository postHashTagRepository;
    private final MyWishRepository myWishRepository;

    /**
     * description: 게시물 id를 통해 게시물 가져옴.
     *
     * @param postId -the post id
     * @return the post
     */
    public Post getPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(IllegalAccessError::new);
        return post;
    }

    /**
     * description: 새로운 게시물 작성.
     *
     * @param observePointName - the observe point name
     * @param postParams - the post params
     * @return the post.getPostId()
     */
    public Long createPost(String observePointName, PostParams postParams,Long areaId) {
        Post post = new Post();
        Observation observation = observationRepository.findByObservationName(observePointName);
        Long observationId = observation.getObservationId();
        post.setPostContent(postParams.getPostContent());
        post.setPostTitle(postParams.getPostTitle());
        post.setOptionObservation(postParams.getOptionObservation());
        post.setYearDate(postParams.getYearDate());
        post.setTime(postParams.getTime());
        post.setWriteDate(postParams.getWriteDate());
        post.setWriteTime(postParams.getWriteTime());
        post.setUser(userRepository.findById(postParams.getUserId()).orElseThrow(IllegalAccessError::new));
        post.setUserId(postParams.getUserId());
        post.setObservation(observation);
        post.setObservationId(observationId);
        if(areaId==0){
            post.setAreaCode(observation.getAreaCode());
        }else{
         post.setAreaCode(areaId);
        }
        post.setLiked(0L);
        post.setSaved(0L);
        postRepository.save(post);
        return post.getPostId();
    }

    /**
     * description: 프로필 페이지에서 내가 쓴 게시물 가져오는 메소드.
     *
     * @param userId - the userId
     * @return the my post 3
     */
    public List<PostParams2> getMyPost3(Long userId) {
        List<PostParams2> result = new ArrayList<>();
        List<Post> list = postRepository.findByUserId(userId);
        List<Post> three = new ArrayList<>();
        int len = list.size();
        if (len > 3){
            for (int i=len-1; i>len-4; i--){
                three.add(list.get(i));
            }
        } else {
            three = list;
        }

        for (Post post : three){
            PostParams2 postParams2 = new PostParams2();
            postParams2.setTitle(post.getPostTitle());
            List<PostImage> imageList = postImageRepository.findByPostId(post.getPostId());
            if (!imageList.isEmpty()) {
                PostImage postImage = imageList.get(0);
                postParams2.setThumbnail(postImage.getImageName());
            } else{
                postParams2.setThumbnail(null);
            }

            result.add(postParams2);
        }
        return result;
    }

    /**
     * description: 게시물 삭제 메소드.
     *
     * @param postId the post id
     */
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
        myWishRepository.deleteByItemIdAndWishType(postId, 2);
    }

    /**
     * description: 내 게시물 페이지에서 내가 쓴 게시물, 북마크 페이지에서 내가 찜하 게시물 가져오는 메소드.
     *
     * @param userId - the user id
     * @return the my post
     */
    public List<PostParams3> getMyPost(Long userId) {
        List<PostParams3> result = new ArrayList<>();
        List<Post> posts = postRepository.findByUserId(userId);
        for (Post post : posts){
            PostParams3 postParams3 = new PostParams3();
            postParams3.setItemId(post.getPostId());

            List<PostImage> imageList = postImageRepository.findByPostId(post.getPostId());
            if (!imageList.isEmpty()) {
                PostImage postImage = imageList.get(0);
                postParams3.setThumbnail(postImage.getImageName());
            } else{
                postParams3.setThumbnail(null);
            }
            postParams3.setTitle(post.getPostTitle());

            User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
            postParams3.setNickName(user.getNickName());
            postParams3.setProfileImage(user.getProfileImage());

            List<String> hashTagName = new ArrayList<>();
            List<PostHashTag> list = postHashTagRepository.findByPostId(post.getPostId());
            int i = 0;
            for(PostHashTag postHashTag : list){
                if(i > 2)
                    break;
                hashTagName.add(postHashTag.getHashTagName());
                i++;
            }
            postParams3.setHashTagNames(hashTagName);

            result.add(postParams3);
        }
        return result;
    }

    /**
     * description: 관측지 페이지에서 관련 게시물 가져오는 메소드.
     *
     * @param observationId - the observation id
     * @return the relate post
     */
    public List<PostParams5> getRelatePost(Long observationId) {
        List<PostParams5> result1 = new ArrayList<>();
        List<Post> relatePosts = postRepository.findByObservationId(observationId);
        for (Post relatePost : relatePosts){
            PostParams5 postParams5 = new PostParams5();
            postParams5.setItemId(relatePost.getPostId());

            List<PostImage> imageList = postImageRepository.findByPostId(relatePost.getPostId());
            if (!imageList.isEmpty()) {
                PostImage postImage = imageList.get(0);
                postParams5.setThumbnail(postImage.getImageName());
            } else{
                postParams5.setThumbnail(null);
            }
            postParams5.setTitle(relatePost.getPostTitle());
            Optional<User> userOp = userRepository.findById(relatePost.getUserId());
            if (userOp.isPresent()){
                User user = userOp.get();
                postParams5.setNickName(user.getNickName());
                postParams5.setProfileImage(user.getProfileImage());
            }
            List<String> hashTagName = new ArrayList<>();
            List<PostHashTag> list = postHashTagRepository.findByPostId(relatePost.getPostId());
            for(PostHashTag postHashTag : list){
                hashTagName.add(postHashTag.getHashTagName());
            }
            postParams5.setHashTagNames(hashTagName);

            result1.add(postParams5);
        }
        return result1;
    }

    /**
     * description: 메인 페이지 게시물 리스트 가져오는 메소드.
     *
     * @return the list
     */
    public List<PostParams4>getMainPost() {
        // 유저의 희망 해시태그에 따라 우선적 필터로 거르고 가져옴)
        List<PostParams4> result = new ArrayList<>();
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Order.desc("postId")));
        for (Post post : posts) {
                PostParams4 postParams4 = new PostParams4();
                postParams4.setPostId(post.getPostId());
                postParams4.setObservationId(post.getObservationId());
                postParams4.setMainTitle(post.getPostTitle());
                Optional<User> userOp = userRepository.findById(post.getUserId());
                if (userOp.isPresent()) {
                    User user = userOp.get();
                    postParams4.setMainNickName(user.getNickName());
                    postParams4.setProfileImage(user.getProfileImage());
                }
                postParams4.setMainObservation(post.getObservation().getObservationName());
                postParams4.setOptionObservation(post.getOptionObservation());
                List<PostImage> mainImageList = postImageRepository.findByPostId(post.getPostId());
                if (!mainImageList.isEmpty()) {
                    ArrayList<String> imageList = new ArrayList<>();
                    for (int i = 0; i < mainImageList.size(); i++) {
                        imageList.add("https://starry-night.s3.ap-northeast-2.amazonaws.com/postImage/" + mainImageList.get(i).getImageName());
                    }
                    postParams4.setImages(imageList);
                } else {
                    postParams4.setImages(null);
                }
                List<String> mainHashTagName = new ArrayList<>();
                List<PostHashTag> list = postHashTagRepository.findByPostId(post.getPostId());
                if (!list.isEmpty()) {
                    for (PostHashTag postHashTag : list) {
                        mainHashTagName.add(postHashTag.getHashTagName());
                    }
                    postParams4.setHashTags(mainHashTagName);
                    if(post.getOptionHashTag()!=null){ postParams4.setOptionHashTag(post.getOptionHashTag());}
                    if(post.getOptionHashTag2()!=null){ postParams4.setOptionHashTag2(post.getOptionHashTag2());}
                    if(post.getOptionHashTag3()!=null){ postParams4.setOptionHashTag3(post.getOptionHashTag3());}
                } else {
                    postParams4.setHashTags(null);
                    if(post.getOptionHashTag()!=null){ postParams4.setOptionHashTag(post.getOptionHashTag());}
                    if(post.getOptionHashTag2()!=null){ postParams4.setOptionHashTag2(post.getOptionHashTag2());}
                    if(post.getOptionHashTag3()!=null){ postParams4.setOptionHashTag3(post.getOptionHashTag3());}
                }
                result.add(postParams4);
        }
        return result;
        }

    /**
     * description: 검색어, 해시태그 필터로 게시물 검색 메소드 입니다.
     *
     * @param filter    the filter
     * @param searchKey the search key
     * @return List<PostParams6>
     */
    public List<SearchParams1>getPostDataWithFilter(Filter filter, String searchKey){
        List<Long> areaCodeList = filter.getAreaCodeList();
        List<Long> hashTagIdList= filter.getHashTagIdList();//해시태그 리스트
        List<SearchParams1>result = new ArrayList<>();
        List<Long>postIdList = new ArrayList<>();
        List<Long> filterIdList = new ArrayList<>();
        List<Post> searchList = new ArrayList<>();
        List<Post> keyList = new ArrayList<>();
        for (Long hashTagId: hashTagIdList){
            List<PostHashTag> postHashTagList= postHashTagRepository.findByHashTagId(hashTagId);
            for (PostHashTag postHashTag : postHashTagList){
                Long postId = postHashTag.getPostId();
                if (!postIdList.contains(postId)){
                    postIdList.add(postId);
                }
            }
        }
        if (!areaCodeList.isEmpty()) {
            for (Long areaCode : areaCodeList) {
                List<Post> postList = postRepository.findByAreaCode(areaCode);
                //관측지에 아직 지역코드 추가 안해서 아래줄로 대체해 놓았음
//                List<Observation> observationList = observationRepository.findAll();
                if (hashTagIdList.isEmpty()) {
                    //해쉬태그 없으면 지역결과 전부추가
                    for (Post post : postList) {
                        filterIdList.add(post.getPostId());
                    }
                } else {
                    //해쉬태그 있으면 필터 중첩
                    for (Post post : postList) {
                        Long postId = post.getPostId();
                        if (postIdList.contains(postId)) {
                            //해시태그결과에서 지역 있으면 filter최종결과에 포함
                            filterIdList.add(postId);
                        }
                    }
                }
            }
        } else {
            //area 비어있으면
            filterIdList = postIdList;
        }

        if (searchKey!=null){
        searchList = postRepository.findByPostTitleContainingOrPostContentContaining(searchKey,searchKey);
        keyList = postRepository.findByPostTitleContainingOrPostContentContaining(searchKey,searchKey);
        if (!hashTagIdList.isEmpty()||!areaCodeList.isEmpty()) {
            //필터 받은게 없으면 그냥 검색결과 전달, 있으면 중첩 검색
            for (Post post : keyList) {
                //전체 검색어 결과 돌면서
                if (!filterIdList.contains(post.getPostId())) {
                    //필터결과에 검색어 결과 없으면 필터+검색어검색결과에서 삭제
                    searchList.remove(post);
                }
            }
        }
        }else{
            for (Long id: filterIdList){
                searchList.add(getPost(id));
            }
        }
        for (Post post : searchList){
            SearchParams1 postParams6 = new SearchParams1();
            postParams6.setItemId(post.getPostId());
            postParams6.setTitle(post.getPostTitle());
            List<String> hashTagName = new ArrayList<>();
            List<PostHashTag> list = postHashTagRepository.findByPostId(post.getPostId());
            int k = 0;
            for(PostHashTag postHashTag : list){
                if(k>2)
                    break;
                hashTagName.add(postHashTag.getHashTagName());
                k++;
            }
            postParams6.setHashTagNames(hashTagName);
            List<PostImage> imageList = postImageRepository.findByPostId(post.getPostId());
            if (!imageList.isEmpty()) {
                PostImage postImage = imageList.get(0);
                postParams6.setThumbnail(postImage.getImageName());
            } else{
                postParams6.setThumbnail(null);
            }
            postParams6.setSaved(post.getSaved());
            result.add(postParams6);
        }
        return result;
    }
    class OrderComparator implements Comparator<Long> {
        @Override
        public int compare(Long o1, Long o2) {
            if (o1 < o2) {
                return 1;
            } else if (o1 > o2) {
                return -1;
            }
            return 0;
        }
    }
}
