package com.server.tourApiProject.bigPost.postComment;

import ch.qos.logback.core.pattern.PostCompileProcessor;
import com.server.tourApiProject.bigPost.post.Post;
import com.server.tourApiProject.bigPost.post.PostRepository;
import com.server.tourApiProject.bigPost.postImage.PostImage;
import com.server.tourApiProject.bigPost.postImage.PostImageRepository;
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
 * className : com.server.tourApiProject.bigPost.postComment
 * description : 설명
 * modification : 2023-01-25(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-01-25
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-01-25       jinhyeok       최초생성
 */
public class PostCommentService {
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;
    private final PostImageRepository postImageRepository;


    /**
     * description: 댓글 id를 통해 게시물 댓글 가져옴.
     *
     * @param postCommentId -the postComment id
     * @return the postComment
     */
    public PostComment getPostComment(Long postCommentId){
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(IllegalAccessError::new);
        return postComment;
    }

    /**
     * description:게시물 댓글 생성하는 메소드.
     *
     * @param postId            - the post id
     * @param postCommentParams - the postComment params
     */
    public void createComments(Long postId, PostCommentParams postCommentParams) {
            Post post = postRepository.findById(postId).orElseThrow(IllegalAccessError::new);
            PostComment postComment = new PostComment();
            postComment.setComment(postCommentParams.getComment());
            postComment.setPost(post);
            postComment.setPostId(post.getPostId());
            postComment.setUser(userRepository.findById(postCommentParams.getUserId()).orElseThrow(IllegalAccessError::new));
            postComment.setUserId(postCommentParams.getUserId());
            postComment.setYearDate(postCommentParams.getYearDate());
            postComment.setTime(postCommentParams.getTime());
            postCommentRepository.save(postComment);
    }
    /**
     * description:게시물 아이디로 게시물 댓글 가져오는 메소드.
     *
     * @param postId            - the post id
     * @return List<PostCommentParams> - the postCommentParams list
     */
    public List<PostCommentParams> getPostCommentById(Long postId){
        List<PostComment> postComments = postCommentRepository.findByPostId(postId);
        List<PostCommentParams> result = new ArrayList<>();
        for(PostComment postComment : postComments){
            PostCommentParams postCommentParams = new PostCommentParams();
            postCommentParams.setCommentId(postComment.getCommentId());
            postCommentParams.setComment(postComment.getComment());
            postCommentParams.setUserId(postComment.getUserId());
            postCommentParams.setTime(postComment.getTime());
            postCommentParams.setYearDate(postComment.getYearDate());
            result.add(postCommentParams);
        }
        return result;
    }

    /**
     * description:내가 쓴 게시물 가져오는 메소드
     *
     * @param userId            - the user id
     * @return List<PostCommentParams2> - the postCommentParams2 list
     */
    public List<PostCommentParams2> getPostCommentByUserId(Long userId){
        List<PostComment> postComments = postCommentRepository.findByUserId(userId);
        List<PostCommentParams2> result = new ArrayList<>();
        for(PostComment postComment : postComments){
            PostCommentParams2 postCommentParams2 = new PostCommentParams2();
            postCommentParams2.setCommentId(postComment.getCommentId());
            postCommentParams2.setComment(postComment.getComment());
            postCommentParams2.setYearDate(postComment.getYearDate());
            Post post = postRepository.findById(postComment.getPostId()).orElseThrow(IllegalAccessError::new);
            postCommentParams2.setPostTitle(post.getPostTitle());
            postCommentParams2.setPostId(post.getPostId());
            List<PostImage> imageList = postImageRepository.findByPostId(post.getPostId());
            if (!imageList.isEmpty()) {
                PostImage postImage = imageList.get(0);
                postCommentParams2.setThumbnail(postImage.getImageName());
            } else{
                postCommentParams2.setThumbnail(null);
            }
            result.add(postCommentParams2);
        }
        return result;
    }

    /**
     * description:게시물 댓글 좋아요 유저 리스트 추가하는 메소드.
     *
     * @param userId            - the user id
     * @param postCommentId - the postComment id
     */
    public void addLove(Long userId, Long postCommentId){
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(IllegalAccessError::new);
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
    }
    /**
     * description:게시물 댓글 좋아요 유저 리스트 삭제하는 메소드.
     *
     * @param userId            - the user id
     * @param postCommentId - the postComment id
     */
    public void removeLove(Long userId, Long postCommentId){
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow(IllegalAccessError::new);
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
    }

    /**
     * description:게시물 댓글 삭제하는 메소드.
     */
    public void deletePostComment(Long postCommentId){postCommentRepository.deleteById(postCommentId);}
}
