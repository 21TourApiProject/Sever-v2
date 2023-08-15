package com.server.tourApiProject.subBanner;

import com.server.tourApiProject.alarm.Alarm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
/**
 * className : com.server.tourApiProject.subBanner
 * description : 서브 배너 서비스
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
public class SubBannerService {
    private final SubBannerRepository subBannerRepository;

    /**
     *  description: 서브 배너를 생성하는 메소드.
     *
     * @param subBanner the subBanner
     */
    public void createSubBanner(SubBanner subBanner) {
        subBannerRepository.save(subBanner);
    }

    /**
     * description: 가장 마지막 서브 배너 가져오는 메소드.
     *
     * @return the subBanner
     */
    public SubBanner getLastSubBanner() {
        return subBannerRepository.findFirstByOrderBySubBannerIdDesc();
    }


}
