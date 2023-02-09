package com.server.tourApiProject.bigPost.postComment;

import com.server.tourApiProject.bigPost.post.Post;
import com.server.tourApiProject.bigPost.post.PostRepository;
import com.server.tourApiProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
            postComment.setLike("0");
            postCommentRepository.save(postComment);
    }

    /**
     * description:게시물 댓글 삭제하는 메소드.
     */
    public void deletePostComment(){postCommentRepository.deleteAll();}
}
