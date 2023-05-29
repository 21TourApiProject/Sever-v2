package com.server.tourApiProject.weather.observationalFit;

import com.server.tourApiProject.weather.fineDust.FineDustService;
import com.server.tourApiProject.weather.observation.WeatherObservation;
import com.server.tourApiProject.weather.observation.WeatherObservationService;
import com.server.tourApiProject.weather.observationalFit.model.Daily;
import com.server.tourApiProject.weather.observationalFit.model.Hourly;
import com.server.tourApiProject.weather.observationalFit.model.OpenWeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ObservationalFitService {

    private final FineDustService fineDustService;
    private final WeatherObservationService weatherObservationService;
    private final ObservationalFitRepository observationalFitRepository;
    private final WebClient webClient;

    private static final String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String OPEN_WEATHER_API_KEY = "7c7ba4d9df15258ce566f6592d875413";
    private static final String OPEN_WEATHER_EXCLUDE = "current,minutely,alerts";
    private static final String OPEN_WEATHER_UNITS = "metric";
    private static final String OPEN_WEATHER_LANG = "kr";


    public void setObservationFit(String date) {
        Map<String, String> fineDustMap = fineDustService.getFineDustMap(date);
        log.info("Fine Dust Info | {}", fineDustMap);

        for(WeatherObservation observation : weatherObservationService.get2Observation()){
//        for(WeatherObservation observation : weatherObservationService.getAllObservation()){
            String fineDust = fineDustMap.getOrDefault(observation.getAddress(), "보통");
            saveBestObservationalFit(date, fineDust, observation);
        }
    }

    // 관측지의 일일 최고 관측 적합도를 구해 저장하는 메서드
    public void saveBestObservationalFit(String date, String fineDust, WeatherObservation observation) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(OPEN_WEATHER_URL);
        uriBuilder.queryParam("lat", observation.getLatitude());
        uriBuilder.queryParam("lon", observation.getLongitude());
        uriBuilder.queryParam("exclude", OPEN_WEATHER_EXCLUDE);
        uriBuilder.queryParam("appid", OPEN_WEATHER_API_KEY);
        uriBuilder.queryParam("units", OPEN_WEATHER_UNITS);
        uriBuilder.queryParam("lang", OPEN_WEATHER_LANG);

        webClient.get()
                .uri(uriBuilder.build().toUri())
                .retrieve()
                .toEntity(OpenWeatherResponse.class)
                .doOnNext(response -> {
                    log.info("HTTP Response for Open Weather ({}, {}) | {} | {}", observation.getLatitude(), observation.getLongitude(), response.getStatusCode(), response.getBody());

                    OpenWeatherResponse openWeatherResponse = response.getBody();
                    Daily daily1 = openWeatherResponse.getDaily().get(0); // +0일
                    Daily daily2 = openWeatherResponse.getDaily().get(1); // +1일

                    double[] observationFitList = new double[13];
                    for (int i = 0; i < 13; i++) {
                        Hourly hourly = openWeatherResponse.getHourly().get(i + 1);
                        if (i < 6) { // 금일 18 ~ 23시 (6개)
                            observationFitList[i] = getObservationFit(
                                    hourly.getClouds(),
                                    daily1.getMoonPhase(),
                                    hourly.getFeelsLike(),
                                    fineDust,
                                    Double.valueOf(hourly.getPop()),
                                    observation.getLightPollution()
                            );
                        } else { // 명일 0시 ~ 6시 (7개)
                            observationFitList[i] = getObservationFit(
                                    hourly.getClouds(),
                                    daily2.getMoonPhase(),
                                    hourly.getFeelsLike(),
                                    fineDust,
                                    Double.valueOf(hourly.getPop()),
                                    observation.getLightPollution()
                            );
                        }
                    }
//                    Arrays.stream(observationFitList).forEach(System.out::println);
                    double bestObservationFit = Arrays.stream(observationFitList).max().getAsDouble();
                    observationalFitRepository.save(ObservationalFit.builder()
                            .bestObservationFit(bestObservationFit)
                            .observationCode(observation.getObservationId())
                            .date(date)
                            .build());
                })
//                    .retryWhen(Retry.fixedDelay(1, Duration.ofSeconds(5)))
                .subscribe();
    }

    public double getObservationFit(Double clouds, Double moonPhase, Double feel_like,
                                    String fineDust, Double pop, Double lightPollution) {

        double cloudsValue; // 구름양
        double moonPhaseValue; // 월령
        double feel_likeValue; // 체감 온도
        double fineDustValue; // 미세먼지
        double popValue; // 강수확률
        double lightPollutionValue; // 광공해

        double biggestValue; //
        double averageValue; //

        double observationalFitDegree;
        double obFitFinal;

        cloudsValue = Math.round(100 * (-(1 / (-(0.25) * (clouds / 100 - 2.7)) - 1.48148)) * 100) / 100.0;
        if (moonPhase <= 0.5) {
            moonPhaseValue = -Math.round(((8 * Math.pow(moonPhase, 3.46)) / 0.727 * 100) * 100) / 100.0;
        } else if (moonPhase > 0.5 && moonPhase <= 0.5609) {
            moonPhaseValue = -Math.round(((-75 * Math.pow(moonPhase - 0.5, 2) + 0.727) / 0.727 * 100) * 100) / 100.0;
        } else {
            moonPhaseValue = -Math.round(((1 / (5.6 * Math.pow(moonPhase + 0.3493, 10)) - 0.0089) / 0.727 * 100) * 100) / 100.0;
        }

        if (feel_like < 18) {
            feel_likeValue = Math.round(-0.008 * Math.pow((feel_like - 18), 2) * 100) / 100.0;
        } else {
            feel_likeValue = Math.round(-0.09 * Math.pow((feel_like - 18), 2) * 100) / 100.0;
        }

        switch (fineDust) {
            case "보통":
                fineDustValue = -5D;
                break;
            case "나쁨":
                fineDustValue = -15D;
                break;
            case "매우나쁨":
                fineDustValue = -30D;
                break;
            default:
                fineDustValue = 0D;
                break;
        }

        popValue = Math.round(100 * (-(1 / (-(1.2) * (pop / 100 - 1.5)) - 0.55556)) * 100) / 100.0;

        if (lightPollution < 28.928) {
            lightPollutionValue = Math.round(-(1 / (-(0.001) * (lightPollution - 48)) - 20.833) * 100) / 100.0;
        } else if (lightPollution >= 28.928 && lightPollution < 77.53) {
            lightPollutionValue = Math.round(-(1 / (-(0.0001) * (lightPollution + 52)) + 155) * 100) / 100.0;
        } else if (lightPollution >= 77.53 && lightPollution < 88.674) {
            lightPollutionValue = Math.round(-(1 / (-(0.001) * (lightPollution - 110)) + 47) * 100) / 100.0;
        } else {
            lightPollutionValue = Math.round(-(1 / (-(0.01) * (lightPollution - 71)) + 100) * 100) / 100.0;
        }

        if (cloudsValue < feel_likeValue && cloudsValue < moonPhaseValue && cloudsValue < fineDustValue && cloudsValue < popValue && cloudsValue < lightPollutionValue) {
            biggestValue = cloudsValue;
        } else if (feel_likeValue < cloudsValue && feel_likeValue < moonPhaseValue && feel_likeValue < fineDustValue && feel_likeValue < popValue && feel_likeValue < lightPollutionValue) {
            biggestValue = feel_likeValue;
        } else if (moonPhaseValue < cloudsValue && moonPhaseValue < feel_likeValue && moonPhaseValue < fineDustValue && moonPhaseValue < popValue && moonPhaseValue < lightPollutionValue) {
            biggestValue = moonPhaseValue;
        } else if (fineDustValue < cloudsValue && fineDustValue < feel_likeValue && fineDustValue < moonPhaseValue && fineDustValue < popValue && fineDustValue < lightPollutionValue) {
            biggestValue = fineDustValue;
        } else if (popValue < cloudsValue && popValue < feel_likeValue && popValue < moonPhaseValue && popValue < fineDustValue && popValue < lightPollutionValue) {
            biggestValue = popValue;
        } else {
            biggestValue = lightPollutionValue;
        }

        averageValue = (cloudsValue + feel_likeValue + moonPhaseValue + fineDustValue + popValue + lightPollutionValue - biggestValue) / 6;

        if (100 + (biggestValue + averageValue * 0.3) > 0) {
            observationalFitDegree = 100 + (biggestValue + averageValue * 0.3);
        } else {
            observationalFitDegree = 0D;
        }

        obFitFinal = Math.round(observationalFitDegree * 100) / 100.0;

        if (obFitFinal < 0) {
            return 0;
        } else {
            return obFitFinal;
        }
    }

}


