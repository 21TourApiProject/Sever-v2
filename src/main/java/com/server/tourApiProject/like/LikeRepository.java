package com.server.tourApiProject.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * className : com.server.tourApiProject.like
 * description : 게시글 좋아요 Repository
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
public interface LikeRepository extends JpaRepository<Like , Long> {
    Optional<Like> findByUserIdAndItemIdAndLikeType(@Param("userId") Long userId,
                                                    @Param("itemId") Long itemId,
                                                    @Param("likeType") Integer likeType);

    List<Like> findByItemIdAndLikeType(@Param("itemId") Long itemId,
                                       @Param("likeType") Integer likeType);
}
