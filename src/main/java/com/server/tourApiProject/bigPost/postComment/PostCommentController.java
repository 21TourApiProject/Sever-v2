package com.server.tourApiProject.bigPost.postComment;

import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"2.4 게시물 댓글"})
@RestController
@RequestMapping(value = "/v2")
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
public class PostCommentController {

    private final PostCommentService postCommentService;

    @ApiOperation(value = "게시물 댓글 정보 입력", notes = "게시물 댓글 정보를 입력한다")
    @PostMapping(value = "postComment /{postId}")
    public void createPostComment(@PathVariable("postId") Long postId,
                                  @RequestBody PostCommentParams postCommentParams)
        throws FirebaseMessagingException {
        postCommentService.createComments(postId,postCommentParams);
    }

    @ApiOperation(value = "게시물 댓글 정보 조회", notes = "게시물 댓글 아이디로 게시물 댓글을 조회한다")
    @GetMapping(value = "postComment /{postCommentId}")
    public PostComment getPostComment(@PathVariable("postCommentId") Long postCommentId) {
        return postCommentService.getPostComment(postCommentId);
    }

    @ApiOperation(value = "게시물 댓글 목록 정보 조회", notes = "게시물 아이디로 게시물 댓글을 조회한다")
    @GetMapping(value = "postCommentList /{postId}")
    public List<PostCommentParams> getPostCommentById(@PathVariable("postId") Long postId) {
        return postCommentService.getPostCommentById(postId);
    }

    @ApiOperation(value = "내가 쓴 댓글 목록 정보 조회", notes = "유저 아이디로 내가 쓴 댓글을 조회한다")
    @GetMapping(value = "myCommentList/{userId}")
    public List<PostCommentParams2> getPostCommentByUserId(@PathVariable("userId") Long userId) {
        return postCommentService.getPostCommentByUserId(userId);
    }

    @ApiOperation(value = "게시물 댓글 좋아요 추가", notes = "댓글 아이디 와 유저 아이디로 게시글 좋아요 추가")
    @GetMapping(value = "postComment /{userId}/{postCommentId}")
    public void addLove(@PathVariable("userId") Long userId,
                        @PathVariable("postCommentId") Long postCommentId) {
            postCommentService.addLove(userId, postCommentId);
    }
    @ApiOperation(value = "게시물 댓글 좋아요 삭제", notes = "댓글 아이디 와 유저 아이디로 게시글 좋아요 삭제")
    @DeleteMapping(value = "postComment /{userId}/{postCommentId}")
    public void removeLove(@PathVariable("userId") Long userId,
                        @PathVariable("postCommentId") Long postCommentId) {
        postCommentService.removeLove(userId, postCommentId);
    }

    @ApiOperation(value = "게시물 댓글 정보 삭제", notes = "게시물 댓글 정보를 삭제한다")
    @DeleteMapping(value = "postComment/{postCommentId}")
    public void deletePost(@PathVariable("postCommentId") Long postCommentId){
        postCommentService.deletePostComment(postCommentId);
    }

}
