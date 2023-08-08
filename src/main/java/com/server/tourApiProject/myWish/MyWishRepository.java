package com.server.tourApiProject.myWish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface MyWishRepository extends JpaRepository<MyWish, Long> {

    List<MyWish> findByUserIdAndWishType(@Param("userId") Long userId, @Param("wishType") Integer wishType);

    List<MyWish> findByUserId(@Param("userId") Long userId);

    Optional<MyWish> findByUserIdAndItemIdAndWishType(@Param("userId") Long userId, @Param("itemId") Long itemId, @Param("wishType") Integer wishType);

    @Transactional
    @Modifying
    @Query("delete from MyWish w where w.itemId = :itemId and w.wishType = :wishType")
    void deleteByItemIdAndWishType(@Param("itemId") Long itemId, @Param("wishType") Integer wishType);

    @Query("select w.itemId as itemId, w.wishType as wishType, count(w.myWishId) as count from MyWish w where w.wishType <> 1 group by w.wishType, w.itemId")
    List<WishCountParams.WishCount> findWishCount();
}
