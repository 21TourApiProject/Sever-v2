package com.server.tourApiProject.weather.observationalFit;

import com.server.tourApiProject.common.Const;
import com.server.tourApiProject.weather.area.WeatherArea;
import com.server.tourApiProject.weather.area.WeatherAreaService;
import com.server.tourApiProject.weather.fineDust.FineDustService;
import com.server.tourApiProject.weather.observation.WeatherObservation;
import com.server.tourApiProject.weather.observation.WeatherObservationService;
import com.server.tourApiProject.weather.observationalFit.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ObservationalFitService {

    private final FineDustService fineDustService;
    private final WeatherAreaService weatherAreaService;
    private final WeatherObservationService weatherObservationService;
    private final ObservationalFitRepository observationalFitRepository;
    private final WebClient webClient;

    private static final String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String OPEN_WEATHER_API_KEY = "7c7ba4d9df15258ce566f6592d875413";
    private static final String OPEN_WEATHER_EXCLUDE = "current,minutely,alerts";
    private static final String OPEN_WEATHER_UNITS = "metric";
    private static final String OPEN_WEATHER_LANG = "kr";

    public Mono<ObservationalInfo> getWeatherInfo(AreaTimeDTO areaTime) {
        return Mono.zip(Mono.just(fineDustService.getFineDustMap(areaTime.getDate())),
                        getOpenWeather(areaTime.getLat(), areaTime.getLon()))
                .flatMap(zip -> {
                    Map<String, String> fineDustMap = zip.getT1();
                    OpenWeatherResponse openWeatherResponse = zip.getT2();

                    String fineDust = null;
                    Double lightPollution = null;

                    if (areaTime.getAreaId() != null) {
                        WeatherArea area = weatherAreaService.getWeatherArea(areaTime.getAreaId());
                        if (Objects.equals(area.getSGG(), "강원") || Objects.equals(area.getSGG(), "경기")) {
                            fineDust = fineDustMap.getOrDefault(area.getSGG2(), "보통");
                        } else {
                            fineDust = fineDustMap.getOrDefault(area.getSGG(), "보통");
                        }
                        lightPollution = area.getLightPollution();
                    } else if (areaTime.getObservationId() != null) {
                        WeatherObservation observation = weatherObservationService.getWeatherObservation(areaTime.getObservationId());
                        fineDust = fineDustMap.getOrDefault(observation.getAddress(), "보통");
                        lightPollution = observation.getLightPollution();
                    }

                    // 상세 날씨 정보
                    ObservationalInfo.DetailWeather detailWeather = new ObservationalInfo.DetailWeather();

                    Hourly hourly = openWeatherResponse.getHourly().get(0); // 현재 Hour 기준 날씨
                    Daily daily = openWeatherResponse.getDaily().get(0); // 현재 Date 기준 날씨

                    detailWeather.setWeatherText(hourly.getWeather().get(0).getDescription());
                    detailWeather.setTempHighest(String.valueOf(Math.round(daily.getTemp().getMax())));
                    detailWeather.setTempLowest(String.valueOf(Math.round(daily.getTemp().getMin())));
                    detailWeather.setRainfallProbability(Double.parseDouble(hourly.getPop()) * 100 + Const.Weather.PERCENT);
                    detailWeather.setHumidity(hourly.getHumidity() + Const.Weather.PERCENT);
                    detailWeather.setCloud(hourly.getClouds() + Const.Weather.PERCENT);
                    detailWeather.setFineDust(fineDust);
                    detailWeather.setWindSpeed(hourly.getWindSpeed() + Const.Weather.METER_PER_SECOND);
                    detailWeather.setMoonAge(getMoonPhaseString(Double.valueOf(daily.getMoonPhase())));
                    detailWeather.setSunrise(getHHmm(daily.getSunrise()));
                    detailWeather.setSunset(getHHmm(daily.getSunset()));
                    detailWeather.setMoonrise(getHHmm(daily.getMoonrise()));
                    detailWeather.setMoonset(getHHmm(daily.getMoonset()));

                    // 시간별 관측적합도 정보
                    List<ObservationalInfo.HourObservationalFit> hourList = new ArrayList<>();
                    Daily H_daily1 = openWeatherResponse.getDaily().get(0); // +0일
                    Daily H_daily2 = openWeatherResponse.getDaily().get(1); // +1일

                    int idx = 0;
                    if(areaTime.getHour() < 18) idx = 17 - areaTime.getHour();

                    for (int i = 0; i < 13; i++) {
                        Hourly H_hourly = openWeatherResponse.getHourly().get(i + idx);
                        double observationalFit;
                        if (i < 6) { // 금일 18 ~ 23시 (6개)
                            observationalFit = getObservationFit(
                                    H_hourly.getClouds(),
                                    H_daily1.getMoonPhase(),
                                    H_hourly.getFeelsLike(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                        } else { // 명일 0시 ~ 6시 (7개)
                            observationalFit = getObservationFit(
                                    H_hourly.getClouds(),
                                    H_daily2.getMoonPhase(),
                                    H_hourly.getFeelsLike(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                        }
                        hourList.add(ObservationalInfo.HourObservationalFit.builder()
                                .hour(String.valueOf(i + 18 < 24 ? i + 18 : i - 6))
                                .observationalFit(Math.round(observationalFit) + Const.Weather.PERCENT).build());
                    }

                    // 일일별 관측적합도 정보
                    List<ObservationalInfo.DayObservationalFit> dayList = new ArrayList<>();
                    Map<Integer, String[]> dayDateMap = getDayDateMap();
                    for (int i = 0; i < 7; i++) {
                        Daily D_daily = openWeatherResponse.getDaily().get(i);
                        FeelsLike feelsLike = D_daily.getFeelsLike();

                        double observationalFit = getObservationFit(
                                D_daily.getClouds(),
                                D_daily.getMoonPhase(),
                                Arrays.stream(new Double[]{feelsLike.getDay(), feelsLike.getNight(), feelsLike.getEve(), feelsLike.getMorn()}).max(Comparator.comparing(x -> x)).get(),
                                fineDust,
                                Double.valueOf(D_daily.getPop()),
                                lightPollution
                        );
                        dayList.add(ObservationalInfo.DayObservationalFit.builder()
                                .day(dayDateMap.get(i)[0])
                                .date(dayDateMap.get(i)[1])
                                .observationalFit(Math.round(observationalFit) + Const.Weather.PERCENT).build());
                    }

                    return Mono.just(ObservationalInfo.builder().detailWeather(detailWeather)
                            .hourObservationalFitList(hourList)
                            .dayObservationalFitList(dayList).build());
                });
    }

    public static Map<Integer, String[]> getDayDateMap() {
        Map<Integer, String[]> map = new HashMap<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M.d");
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("E", Locale.KOREA);

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            if (i == 0) map.put(i, new String[]{"오늘", date.format(dateFormatter)});
            else map.put(i, new String[]{date.format(dayOfWeekFormatter), date.format(dateFormatter)});
        }
        return map;
    }

    // 월령 수치를 문자열로 바꾸는 메서드
    public String getMoonPhaseString(Double moonPhase) {
        if ((0 <= moonPhase && moonPhase <= 0.06) || (0.94 <= moonPhase && moonPhase <= 1)) {
            return Const.Weather.VERY_GOOD;
        } else if ((0.07 <= moonPhase && moonPhase <= 0.13) || (0.85 <= moonPhase && moonPhase <= 93)) {
            return Const.Weather.GOOD;
        } else if ((0.14 <= moonPhase && moonPhase <= 0.21) || (0.75 <= moonPhase && moonPhase <= 0.84)) {
            return Const.Weather.NORMAL;
        } else if ((0.22 <= moonPhase && moonPhase <= 0.35) || (0.63 <= moonPhase && moonPhase <= 0.74)) {
            return Const.Weather.BAD;
        } else {
            return Const.Weather.VERY_BAD;
        }
    }

    // unix UTC 을 HH:mm 로 바꾸는 메서드
    public String getHHmm(String unixTime) {
        Date unixHourMin = new Date(Long.parseLong(unixTime) * 1000L);
        SimpleDateFormat formatHourMin = new SimpleDateFormat("HH:mm");
        formatHourMin.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
        return formatHourMin.format(unixHourMin);
    }

    public void setObservationFit(String date) {
        Map<String, String> fineDustMap = fineDustService.getFineDustMap(date);
        log.info("Fine Dust Info | {}", fineDustMap);

        for (WeatherObservation observation : weatherObservationService.get2Observation()) {
//        for (WeatherObservation observation : weatherObservationService.getAllObservation()) {
            String fineDust = fineDustMap.getOrDefault(observation.getAddress(), "보통");
            saveBestObservationalFit(date, fineDust, observation);
        }
    }

    // 관측지의 일일 최고 관측 적합도를 구해 저장하는 메서드
    public void saveBestObservationalFit(String date, String fineDust, WeatherObservation observation) {
        getOpenWeather(observation.getLatitude(), observation.getLongitude())
                .doOnNext(openWeatherResponse -> {
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
                            .bestObservationalFit(bestObservationFit)
                            .observationCode(observation.getObservationId())
                            .date(date)
                            .build());
                })
//                .retryWhen(Retry.fixedDelay(1, Duration.ofSeconds(5)))
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

    public Mono<OpenWeatherResponse> getOpenWeather(Double lat, Double lon) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(OPEN_WEATHER_URL);
        uriBuilder.queryParam("lat", lat);
        uriBuilder.queryParam("lon", lon);
        uriBuilder.queryParam("exclude", OPEN_WEATHER_EXCLUDE);
        uriBuilder.queryParam("appid", OPEN_WEATHER_API_KEY);
        uriBuilder.queryParam("units", OPEN_WEATHER_UNITS);
        uriBuilder.queryParam("lang", OPEN_WEATHER_LANG);

        return webClient.get()
                .uri(uriBuilder.build().toUri())
                .retrieve()
                .toEntity(OpenWeatherResponse.class)
                .flatMap(response -> {
                    log.info("HTTP Response for Open Weather ({}, {}) | {} | {}", lat, lon,
                            response.getStatusCode(), response.getBody());
                    return Mono.just(Objects.requireNonNull(response.getBody()));
                });
    }

}


