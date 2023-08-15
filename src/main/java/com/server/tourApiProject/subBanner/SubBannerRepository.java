package com.server.tourApiProject.subBanner;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * className : com.server.tourApiProject.subBanner
 * description : 서브 배너 Repository
 * modification : 2023-08-15(jinhyeok) methodA수정
 * author : jinhyeok
 * date : 2023-08-15
 * version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2023-08-15       jinhyeok       최초생성
 */
public interface SubBannerRepository extends JpaRepository<SubBanner, Long> {
    SubBanner findFirstByOrderBySubBannerIdDesc(); //id를 역순으로 나열하고 첫번째만 가져오는 쿼리
}
