package com.server.tourApiProject.myWish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MyWishRepository extends JpaRepository<MyWish, Long> {

    List<MyWish> findByUserIdAndWishType(@Param("userId") Long userId, @Param("wishType") Integer wishType);

    List<MyWish> findByUserId(@Param("userId") Long userId);

    Optional<MyWish> findByUserIdAndItemIdAndWishType(@Param("userId") Long userId, @Param("itemId") Long itemId, @Param("wishType") Integer wishType);

    void deleteByItemIdAndWishType(@Param("itemId") Long itemId, @Param("wishType") Integer wishType);
}
