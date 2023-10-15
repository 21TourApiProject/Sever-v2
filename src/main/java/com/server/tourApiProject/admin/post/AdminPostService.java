package com.server.tourApiProject.admin.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.tourApiProject.bigPost.post.Post;
import com.server.tourApiProject.bigPost.post.PostRepository;
import com.server.tourApiProject.myWish.MyWishRepository;
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
public class AdminPostService {

    private final PostRepository postRepository;
    private final MyWishRepository myWishRepository;
    private final UserRepository userRepository;

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
        myWishRepository.deleteByItemIdAndWishType(postId, 2);
    }

    public List<Post> getPostByTitle(String title) {
        return postRepository.findByPostTitleContaining(title);
    }

    public List<Post> getPostByNickName(String nickname) {
        User user = userRepository.findByNickName(nickname);
        List<Post> result = new ArrayList<>();
        if (user != null) {
            result = postRepository.findByUserId(user.getUserId());
        }
        return result;
    }
}
