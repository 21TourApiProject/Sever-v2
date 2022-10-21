package com.server.tourApiProject.star.Horoscope;

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

/**
* @className : HoroscopeService.java
* @description : 별자리 운세 service 입니다.
* @modification : 2022-08-29 (hyeonz) 주석 추가
* @author : hyeonz
* @date : 2022-08-29
* @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-29     hyeonz       주석 추가
 */
public class HoroscopeService {
    private final HoroscopeRepository horoscopeRepository;

    /**
     * TODO 현재 달의 별자리 운세 정보 조회
     * @param todayMonth - 현재 달
     * @return List<HoroscopeParams>
     */
    public List<HoroscopeParams> getHoroscopes(int todayMonth) {
        List<HoroscopeParams> result = new ArrayList<>();
        List<Horoscope> list = horoscopeRepository.findAll();

        if (todayMonth == 1) {
            return getHoroscopeParams(1, result, list);
        } else if (todayMonth == 2) {
            return getHoroscopeParams(2, result, list);
        } else if (todayMonth == 3) {
            return getHoroscopeParams(3, result, list);
        } else if (todayMonth == 4) {
            return getHoroscopeParams(4, result, list);
        } else if (todayMonth == 5) {
            return getHoroscopeParams(5, result, list);
        } else if (todayMonth == 6) {
            return getHoroscopeParams(6, result, list);
        } else if (todayMonth == 7) {
            return getHoroscopeParams(7, result, list);
        } else if (todayMonth == 8) {
            return getHoroscopeParams(8, result, list);
        } else if (todayMonth == 9) {
            return getHoroscopeParams(9, result, list);
        } else if (todayMonth == 10) {
            return getHoroscopeParams(10, result, list);
        } else if (todayMonth == 11) {
            return getHoroscopeParams(11, result, list);
        } else {
            return getHoroscopeParams(12, result, list);
        }
    }

    /**
     * TODO 현재 달의 별자리 운세 정보 조회
     * @param todayMonth - 현재 달
     * @param result - 별자리 운세 param list
     * @param list - 별자리 운세 lists
     * @return List<HoroscopeParams>
     */
    private List<HoroscopeParams> getHoroscopeParams(int todayMonth, List<HoroscopeParams> result, List<Horoscope> list) {
        for (Horoscope hr : list) {
            Horoscope horoscope = horoscopeRepository.findById(hr.getHorId()).orElseThrow(IllegalAccessError::new);
            HoroscopeParams params = new HoroscopeParams();

            params.setHorImage(horoscope.getHorImage());
            params.setHorEngTitle(horoscope.getHorEngTitle());
            params.setHorKrTitle(horoscope.getHorKrTitle());
            params.setHorPeriod(horoscope.getHorPeriod());
            params.setHorGuard(horoscope.getHorGuard());
            params.setHorPersonality(horoscope.getHorPersonality());
            params.setHorTravel(horoscope.getHorTravel());

            if (todayMonth == 1) {
                params.setHorDesc(horoscope.getHorDesc1());
            } else if (todayMonth == 2) {
                params.setHorDesc(horoscope.getHorDesc2());
            } else if (todayMonth == 3) {
                params.setHorDesc(horoscope.getHorDesc3());
            } else if (todayMonth == 4) {
                params.setHorDesc(horoscope.getHorDesc4());
            } else if (todayMonth == 5) {
                params.setHorDesc(horoscope.getHorDesc5());
            } else if (todayMonth == 6) {
                params.setHorDesc(horoscope.getHorDesc6());
            } else if (todayMonth == 7) {
                params.setHorDesc(horoscope.getHorDesc7());
            } else if (todayMonth == 8) {
                params.setHorDesc(horoscope.getHorDesc8());
            } else if (todayMonth == 9) {
                params.setHorDesc(horoscope.getHorDesc9());
            } else if (todayMonth == 10) {
                params.setHorDesc(horoscope.getHorDesc10());
            } else if (todayMonth == 11) {
                params.setHorDesc(horoscope.getHorDesc11());
            } else if (todayMonth == 12) {
                params.setHorDesc(horoscope.getHorDesc12());
            }

            result.add(params);
        }
        return result;
    }

    /**
     * TODO 모든 별자리 운세 정보 조회
     * @return List<Horoscope>
     */
    public List<Horoscope> getAllHoroscopes() {
        return horoscopeRepository.findAll();
    }
}
