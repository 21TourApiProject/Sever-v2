package com.server.tourApiProject.observation.observeFee;

import com.server.tourApiProject.observation.ObservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
* @className : ObserveFeeService.java
* @description : 관측지 요금 서비스
* @modification : 2022-08-29 (gyul chyoung) 주석추가
* @author : gyul chyoung
* @date : 2022-08-29
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-29       gyul chyoung       주석추가
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ObserveFeeService {
    private final ObserveFeeRepository observeFeeRepository;

    /**
     * TODO 관측지 요금 조회
     * @param  observationId -
     * @return java.util.List<com.server.tourApiProject.observation.observeFee.ObserveFee>
     * @throws
     */
    public List<ObserveFee> getObserveFees(Long observationId) {
        List<ObserveFee> observeFeeList = observeFeeRepository.findByObservationId(observationId);
        return observeFeeList;
    }

}
