package com.server.tourApiProject.weather.observationalFit;

import com.server.tourApiProject.common.Const;
import com.server.tourApiProject.weather.area.WeatherArea;
import com.server.tourApiProject.weather.area.WeatherAreaService;
import com.server.tourApiProject.weather.description.DescriptionRepository;
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
    private final DescriptionRepository descriptionRepository;
    private final WebClient webClient;

    private static final String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String OPEN_WEATHER_API_KEY = "7c7ba4d9df15258ce566f6592d875413";
    private static final String OPEN_WEATHER_EXCLUDE = "current,minutely,alerts";
    private static final String OPEN_WEATHER_UNITS = "metric";
    private static final String OPEN_WEATHER_LANG = "kr";

    private static final Map<Double, String> effectMap = Map.of(0D, "많은 구름", 1D, "나쁜 체감온도", 2D, "밝은 달빛", 3D, "나쁜 미세먼지", 4D, "높은 강수확률", 5D, "높은 광공해");

    public Mono<WeatherInfo> getWeatherInfo(AreaTimeDTO areaTime) {

        log.info("Weather Request from App | {} ", areaTime);

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
                    WeatherInfo.DetailWeather detailWeather = new WeatherInfo.DetailWeather();

                    Hourly hourly = openWeatherResponse.getHourly().get(0); // 현재 Hour 기준 날씨
                    Daily daily = openWeatherResponse.getDaily().get(0); // 현재 Date 기준 날씨

                    detailWeather.setWeatherText(getDescription(hourly.getWeather().get(0).getId()));
                    detailWeather.setTempHighest("최고 " + Math.round(daily.getTemp().getMax()) + "°");
                    detailWeather.setTempLowest("최저 " + Math.round(daily.getTemp().getMin()) + "°");
                    detailWeather.setRainfallProbability(Math.round(Double.parseDouble(hourly.getPop()) * 100) + Const.Weather.PERCENT);
                    detailWeather.setHumidity(hourly.getHumidity() + Const.Weather.PERCENT);
                    detailWeather.setCloud(Math.round(hourly.getClouds()) + Const.Weather.PERCENT);
                    detailWeather.setFineDust(fineDust);
                    detailWeather.setWindSpeed(hourly.getWindSpeed() + Const.Weather.METER_PER_SECOND);
                    detailWeather.setMoonAge(getMoonPhaseString(Double.valueOf(daily.getMoonPhase())));
                    detailWeather.setSunrise(getHHmm(daily.getSunrise()));
                    detailWeather.setSunset(getHHmm(daily.getSunset()));
                    detailWeather.setMoonrise(getHHmm(daily.getMoonrise()));
                    detailWeather.setMoonset(getHHmm(daily.getMoonset()));

                    // 시간별 관측적합도 정보
                    List<WeatherInfo.HourObservationalFit> hourList = new ArrayList<>();
                    Daily H_daily1 = openWeatherResponse.getDaily().get(0); // +0일
                    Daily H_daily2 = openWeatherResponse.getDaily().get(1); // +1일

                    Integer hour = areaTime.getHour();
                    int idx = 0;
                    if (hour < 18) idx = 17 - hour;

                    int bestTime = 18; // 최고 관측적합도 시각
                    double minObservationalFit = 0D; // 최소 관측적합도
                    double maxObservationalFit = 0D; // 최대 관측적합도
                    double maxEffect = 0D; // 최대 관측적합도의 주요 원인

                    // 0    1   2   3   4   5   6   7   8   9   10  11  12
                    // 18   19  20  21  22  23  0   1   2   3   4   5   6
                    int start = 0;
                    if (hour >= 18) start = hour - 18;
                    else if(hour <= 6) start = hour + 6;
                    for (int i = start; i < 13; i++) {
                        Hourly H_hourly = openWeatherResponse.getHourly().get(i + idx);
                        double observationalFit;
                        double effect;
                        if (i < 6) { // 금일 18 ~ 23시 (6개)
                            double[] observationFit = getObservationFit(
                                    H_hourly.getClouds(),
                                    H_daily1.getMoonPhase(),
                                    H_hourly.getFeelsLike(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                            observationalFit = observationFit[0];
                            effect = observationFit[1];
                        } else { // 명일 0시 ~ 6시 (7개)
                            double[] observationFit = getObservationFit(
                                    H_hourly.getClouds(),
                                    H_daily2.getMoonPhase(),
                                    H_hourly.getFeelsLike(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                            observationalFit = observationFit[0];
                            effect = observationFit[1];
                        }
                        if (maxObservationalFit < observationalFit) {
                            maxObservationalFit = observationalFit;
                            maxEffect = effect;
                            bestTime = i + 18 < 24 ? i + 18 : i - 6;
                        }
                        if (minObservationalFit > observationalFit) {
                            minObservationalFit = observationalFit;
                        }
                        hourList.add(WeatherInfo.HourObservationalFit.builder()
                                .hour((i + 18 < 24 ? i + 18 : i - 6) + "시")
                                .observationalFit(Math.round(observationalFit) + Const.Weather.PERCENT).build());
                    }

                    // 일일별 관측적합도 정보
                    List<WeatherInfo.DayObservationalFit> dayList = new ArrayList<>();
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
                        )[0];
                        dayList.add(WeatherInfo.DayObservationalFit.builder()
                                .day(dayDateMap.get(i)[0])
                                .date(dayDateMap.get(i)[1])
                                .observationalFit(Math.round(observationalFit) + Const.Weather.PERCENT).build());
                    }

                    String[] todayComment = getTodayComment(bestTime, minObservationalFit, maxObservationalFit, maxEffect);

                    WeatherInfo weatherInfo = WeatherInfo.builder().detailWeather(detailWeather)
                            .hourObservationalFitList(hourList)
                            .dayObservationalFitList(dayList)
                            .lightPollutionLevel(getLightPollutionLevel(lightPollution))
                            .todayComment1(todayComment[0])
                            .todayComment2(todayComment[1])
                            .bestObservationalFit((int) Math.round(maxObservationalFit))
                            .bestTime(bestTime).build();

                    if (todayComment.length == 3) weatherInfo.setMainEffect(todayComment[2]); // 관측적합도가 60% 미만일시, 원인 제공

                    return Mono.just(weatherInfo);
                });
    }

    // 기상 상태 번역
    public String getDescription(String id) {
        return descriptionRepository.findById(id).getResult();
    }

    // 광공해 값 -> 0 ~ 4 변환
    // 0: 매우좋음, 1: 좋음, 2: 보통, 3: 나쁨, 4: 매우나쁨
    public Integer getLightPollutionLevel(Double lightPollution) {
        if (lightPollution <= 1) return 0;
        else if (lightPollution <= 15) return 1;
        else if (lightPollution <= 45) return 2;
        else if (lightPollution <= 80) return 3;
        else return 4;
    }

    public String[] getTodayComment(int bestTime, double minObservationalFit, double maxObservationalFit, double maxEffect) {
        int min = (int) Math.round(minObservationalFit);
        int max = (int) Math.round(maxObservationalFit);

        if (min >= 85) {
            return new String[]{"오늘은 하루종일", "별 보기 최고에요"};
        } else if (max >= 70) {
            return new String[]{"오늘 " + bestTime + "시가", "별 보기 가장 좋아요"};
        } else if (max >= 60) {
            return new String[]{"오늘 " + bestTime + "시가", "별 보기 적당해요"};
        } else if (max >= 40) {
            return new String[]{"오늘은 하루종일", "별 보기 조금 아쉬워요", effectMap.get(maxEffect)};
        } else {
            return new String[]{"오늘은 하루종일", "별 보기 어려워요", effectMap.get(maxEffect)};
        }
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
                            )[0];
                        } else { // 명일 0시 ~ 6시 (7개)
                            observationFitList[i] = getObservationFit(
                                    hourly.getClouds(),
                                    daily2.getMoonPhase(),
                                    hourly.getFeelsLike(),
                                    fineDust,
                                    Double.valueOf(hourly.getPop()),
                                    observation.getLightPollution()
                            )[0];
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

    public double[] getObservationFit(Double clouds, Double moonPhase, Double feel_like,
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

        double effect;

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
            effect = 0D;
        } else if (feel_likeValue < cloudsValue && feel_likeValue < moonPhaseValue && feel_likeValue < fineDustValue && feel_likeValue < popValue && feel_likeValue < lightPollutionValue) {
            biggestValue = feel_likeValue;
            effect = 1D;
        } else if (moonPhaseValue < cloudsValue && moonPhaseValue < feel_likeValue && moonPhaseValue < fineDustValue && moonPhaseValue < popValue && moonPhaseValue < lightPollutionValue) {
            biggestValue = moonPhaseValue;
            effect = 2D;
        } else if (fineDustValue < cloudsValue && fineDustValue < feel_likeValue && fineDustValue < moonPhaseValue && fineDustValue < popValue && fineDustValue < lightPollutionValue) {
            biggestValue = fineDustValue;
            effect = 3D;
        } else if (popValue < cloudsValue && popValue < feel_likeValue && popValue < moonPhaseValue && popValue < fineDustValue && popValue < lightPollutionValue) {
            biggestValue = popValue;
            effect = 4D;
        } else {
            biggestValue = lightPollutionValue;
            effect = 5D;
        }

        averageValue = (cloudsValue + feel_likeValue + moonPhaseValue + fineDustValue + popValue + lightPollutionValue - biggestValue) / 6;

        if (100 + (biggestValue + averageValue * 0.3) > 0) {
            observationalFitDegree = 100 + (biggestValue + averageValue * 0.3);
        } else {
            observationalFitDegree = 0D;
        }

        obFitFinal = Math.round(observationalFitDegree * 100) / 100.0;

        if (obFitFinal < 0) {
            return new double[]{0D};
        } else {
            return new double[]{obFitFinal, effect};
        }
    }

    public Mono<OpenWeatherResponse> getOpenWeather(Double lat, Double lon) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(OPEN_WEATHER_URL);
        uriBuilder.queryParam("lat", lat);
        uriBuilder.queryParam("lon", lon);
        uriBuilder.queryParam("exclude", OPEN_WEATHER_EXCLUDE);
        uriBuilder.queryParam("appid", OPEN_WEATHER_API_KEY);
        uriBuilder.queryParam("units", OPEN_WEATHER_UNITS);
//        uriBuilder.queryParam("lang", OPEN_WEATHER_LANG);

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

    public Mono<MainInfo> getMainInfo(AreaTimeDTO areaTime) {

        System.out.println("areaTime = " + areaTime.toString());
        Long areaId = getAreaId(areaTime.getAddress());
        System.out.println("areaId = " + areaId);

        return Mono.zip(Mono.just(fineDustService.getFineDustMap(areaTime.getDate())),
                        getOpenWeather(areaTime.getLat(), areaTime.getLon()))
                .flatMap(zip -> {
                    Map<String, String> fineDustMap = zip.getT1();
                    OpenWeatherResponse openWeatherResponse = zip.getT2();

                    String fineDust;
                    Double lightPollution;

                    WeatherArea area = weatherAreaService.getWeatherArea(areaId);
                    if (Objects.equals(area.getSGG(), "강원") || Objects.equals(area.getSGG(), "경기")) {
                        fineDust = fineDustMap.getOrDefault(area.getSGG2(), "보통");
                    } else {
                        fineDust = fineDustMap.getOrDefault(area.getSGG(), "보통");
                    }
                    lightPollution = area.getLightPollution();

                    Daily H_daily1 = openWeatherResponse.getDaily().get(0); // +0일
                    Daily H_daily2 = openWeatherResponse.getDaily().get(1); // +1일

                    int idx = 0;
                    if (areaTime.getHour() < 18) idx = 17 - areaTime.getHour();

                    int bestTime = 18; // 최고 관측적합도 시각
                    double minObservationalFit = 0D; // 최소 관측적합도
                    double maxObservationalFit = 0D; // 최대 관측적합도

                    for (int i = 0; i < 13; i++) {
                        Hourly H_hourly = openWeatherResponse.getHourly().get(i + idx);
                        double observationalFit;
                        if (i < 6) { // 금일 18 ~ 23시 (6개)
                            double[] observationFit = getObservationFit(
                                    H_hourly.getClouds(),
                                    H_daily1.getMoonPhase(),
                                    H_hourly.getFeelsLike(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                            observationalFit = observationFit[0];
                        } else { // 명일 0시 ~ 6시 (7개)
                            double[] observationFit = getObservationFit(
                                    H_hourly.getClouds(),
                                    H_daily2.getMoonPhase(),
                                    H_hourly.getFeelsLike(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                            observationalFit = observationFit[0];
                        }
                        if (maxObservationalFit < observationalFit) {
                            maxObservationalFit = observationalFit;
                            bestTime = i + 18 < 24 ? i + 18 : i - 6;
                        }
                        if (minObservationalFit > observationalFit) {
                            minObservationalFit = observationalFit;
                        }
                    }

                    String bestTimeString;
                    if (bestTime <= 6) {
                        bestTimeString = "추천 관측시간 내일 0" + bestTime + "시";
                    } else {
                        bestTimeString = "추천 관측시간 " + bestTime + "시";
                    }
                    MainInfo mainInfo = MainInfo.builder()
                            .comment(areaTime.getAddress().split(" ")[2] + ",\n" + getMainComment(minObservationalFit, maxObservationalFit))
                            .bestObservationalFit("관측 적합도 ~" + (int) Math.round(maxObservationalFit) + "%")
                            .bestTime(bestTimeString)
                            .areaId(areaId)
                            .build();

                    return Mono.just(mainInfo);
                });
    }


    // https://maps.googleapis.com/maps/api/geocode/json?latlng=37.573427,126.910140&key=AIzaSyB6QyRBRuKS6tleI_eyalLIXnkHGEyw0Kc&language=kr
    //8e9d0698ed2d448e4b441ff77ccef198

    public Long getAreaId(String address) {
        return weatherAreaService.getAreaIdByAddress(address);
    }

    public String getMainComment(double minObservationalFit, double maxObservationalFit) {
        int min = (int) Math.round(minObservationalFit);
        int max = (int) Math.round(maxObservationalFit);

        if (min >= 85) {
            return "별 보기 최고의 날이네요!";
        } else if (max >= 70) {
            return "별 보기 좋은 날이네요!";
        } else if (max >= 60) {
            return "별 보기 괜찮은 날이네요!";
        } else if (max >= 40) {
            return "오늘은 별 보기 조금 아쉽네요";
        } else {
            return "오늘은 별을 보기 어려워요";
        }
    }
}


