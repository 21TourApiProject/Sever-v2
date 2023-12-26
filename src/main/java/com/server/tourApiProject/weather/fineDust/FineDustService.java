package com.server.tourApiProject.weather.fineDust;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.tourApiProject.common.Const;
import com.server.tourApiProject.weather.fineDust.model.AirKoreaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class FineDustService {

    private final WebClient webClient;

    private static final String AIR_KOREA_URL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth";
    private static final String AIR_KOREA_API_KEY = "BdxNGWQJQFutFYE6DkjePTmerMbwG2fzioTf6sr69ecOAdLGMH4iiukF8Ex93YotSgkDOHe1VxKNOr8USSN6EQ==";
    private static final String AIR_KOREA_RETURN_TYPE = "json";
    private static final String AIR_KOREA_INFORM_CODE = "PM10";

    private final ObjectMapper mapper;
    /**
     * 해당 date 의 미세먼지 예보 호출 메서드
     */
    public Map<String, String> getFineDustMap(String date) {

        // 오전 6시 이전에 호출하면 실패. -> 오전 6시 이전 요청은 하루 전 날짜로 요청 필요
        String hour = LocalTime.now().format(DateTimeFormatter.ofPattern("H"));
        if (Integer.parseInt(hour) < 6) date = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(AIR_KOREA_URL);
        uriBuilder.queryParam("searchDate", date);
        uriBuilder.queryParam("serviceKey", AIR_KOREA_API_KEY);
        uriBuilder.queryParam("returnType", AIR_KOREA_RETURN_TYPE);
        uriBuilder.queryParam("informCode", AIR_KOREA_INFORM_CODE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("searchDate", date);
        params.add("serviceKey", AIR_KOREA_API_KEY);
        params.add("returnType", AIR_KOREA_RETURN_TYPE);
        params.add("informCode", AIR_KOREA_INFORM_CODE);

        Map<String, String> result = new HashMap<>();

        webClient.get()
                .uri(uriBuilder.build().toUri())
                .retrieve()
                .toEntity(AirKoreaResponse.class)
                .doOnNext(response -> {
//                    try {
//                        log.info("HTTP Response for Air Korea Get | {} | {}", response.getStatusCode(), mapper.writeValueAsString(response.getBody()));
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    }
                    AirKoreaResponse airKoreaResponse = response.getBody();
                    String informGrade = airKoreaResponse.getResponse().getBody().getItems().get(0).getInformGrade();

                    String[] split1 = informGrade.split(Const.Weather.FINE_DUST_SPLIT_1);
                    for (String s1 : split1) {
                        String[] split2 = s1.split(Const.Weather.FINE_DUST_SPLIT_2);
                        result.put(split2[0], split2[1]);
                    }
                })
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(5)))
                .block();
        return result;
    }

}
