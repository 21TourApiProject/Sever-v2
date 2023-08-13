package com.server.tourApiProject.bigPost.postHashTag;

import com.server.tourApiProject.bigPost.post.Post;
import com.server.tourApiProject.bigPost.post.PostRepository;
import com.server.tourApiProject.hashTag.HashTag;
import com.server.tourApiProject.hashTag.HashTagRepository;
import com.server.tourApiProject.touristPoint.area.Area;
import com.server.tourApiProject.touristPoint.area.AreaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Post hash tag service.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
/**
* @className : PostHashTagService.java
* @description : 게시물 해시태그 Service 입니다. (게시물 해시태그 생성, 삭제 / 게시물의 해시태그 목록 가져오기)
* @modification : 2022-08-08(jinhyeok) 주석 수정
* @author : jinhyeok
* @date : 2022-08-08
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   2022-08-08       jinhyeok       주석 수정

 */
public class PostHashTagService {
    private final PostHashTagRepository postHashTagRepository;
    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;
    private final AreaRepository areaRepository;


    /**
     * description:게시물 id로 해시태그 가져오는 메소드.
     *
     * @param postId - the post id
     * @return the Posthashtag
     */
    public List<PostHashTag> getPostHashTag(Long postId) {
        return postHashTagRepository.findByPostId(postId);
    }

    /**
     * description: 해시태그 클래스에서 해시태그 이름 리스트를 가져오는 메소드.
     *
     * @param postId - the post id
     * @return the post hash tag name
     */
    public List<String> getPostHashTagName(Long postId) {
        List<String> postHashTagNameList =new ArrayList<>();
        List<PostHashTag> postHashTagList = postHashTagRepository.findByPostId(postId);
        for(PostHashTag p : postHashTagList) {
            postHashTagNameList.add(p.getHashTagName());
        }
        return postHashTagNameList;
    }

    /**
     * description:게시물 해시태그 생성하는 메소드.
     *
     * @param postId            - the post id
     * @param postHashTagParams - the post hash tag params
     */
    public void createPostHashTags(Long postId,List<PostHashTagParams> postHashTagParams) {
        for (PostHashTagParams p : postHashTagParams) {
            Post post = postRepository.findById(postId).orElseThrow(IllegalAccessError::new);
            PostHashTag postHashTag = new PostHashTag();
            postHashTag.setPost(post);
            postHashTag.setPostId(post.getPostId());
            if(p.getHashTagName()!=null){
                postHashTag.setHashTagName(p.getHashTagName());
                HashTag hashTag = hashTagRepository.findByHashTagName(p.getHashTagName());
                postHashTag.setHashTagId(hashTag.getHashTagId());
            }
            if(p.getAreaName()!=null){
                postHashTag.setHashTagName(p.getAreaName());
                Area area = areaRepository.findByAreaId(p.getAreaId());
                postHashTag.setHashTagId(p.getAreaId());
            }
            postHashTagRepository.save(postHashTag);
        }
    }

    /**
     * description:게시물 해시태그 삭제하는 메소드.
     */
    public void deletePostHashTags(){postHashTagRepository.deleteAll();}
}
