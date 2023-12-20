package com.server.tourApiProject.fcm;

import com.server.tourApiProject.bigPost.postComment.PostComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * className : com.server.tourApiProject.fcm description : 설명 modification : 2023-12-03(jinhyeok)
 * methodA수정 author : jinhyeok date : 2023-12-03 version : 1.0
 * <p>
 * ====개정이력(Modification Information)==== 수정일        수정자        수정내용
 * ----------------------------------------- 2023-12-03       jinhyeok       최초생성
 */
public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    List<FcmToken> findByUserId (@Param("userId") Long userId);
}
