package com.server.tourApiProject.likes;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * className : com.server.tourApiProject.like
 * description : 설명
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
public class LikeParams {
    private Long itemid;
    private int likeCount;
}
