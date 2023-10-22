package com.server.tourApiProject.admin.post;

import com.server.tourApiProject.bigPost.post.Post;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"99.1 게시물 관리자"})
@RestController
@RequestMapping(value = "/v2/admin")
@RequiredArgsConstructor
public class AdminPostController {

    private final AdminPostService postService;

    @ApiOperation(value = "전체 게시물 조회", notes = "전체 게시물을 조회한다")
    @GetMapping(value = "posts")
    public List<Post> getAllPost() {
        return postService.getAllPost();
    }

    @ApiOperation(value = "게시물 삭제", notes = "특정 게시물을 삭제한다")
    @PostMapping(value = "post/delete")
    public void deletePost(@RequestParam Long postId) {
        postService.deletePost(postId);
    }

    @ApiOperation(value = "게시물 조회 (제목)", notes = "제목으로 게시물을 조회한다")
    @PostMapping(value = "post/title")
    public List<Post> getPostByTitle(@RequestParam String title) {
        return postService.getPostByTitle(title);
    }

    @ApiOperation(value = "게시물 조회 (닉네임)", notes = "닉네임으로 게시물을 조회한다")
    @PostMapping(value = "post/nickName")
    public List<Post> getPostByNickName(@RequestParam String nickName) {
        return postService.getPostByNickName(nickName);
    }
}
