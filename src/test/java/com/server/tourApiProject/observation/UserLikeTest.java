package com.server.tourApiProject.observation;

import com.server.tourApiProject.myWish.MyWishRepository;
import com.server.tourApiProject.myWish.MyWishService;
import com.server.tourApiProject.myWish.WishCountParams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserLikeTest {

    @Autowired
    MyWishService myWishService;

    @Autowired
    MyWishRepository myWishRepository;

    @Test
    void testLiked(){
        List<WishCountParams.WishCount> wishCount = myWishRepository.findWishCount();
        System.out.println(wishCount.size());
    }


}
