package com.server.tourApiProject.weather.observationalFit;

import com.server.tourApiProject.common.Const;
import com.server.tourApiProject.interestArea.model.InterestAreaDetailWeatherInfo;
import com.server.tourApiProject.weather.area.NearestAreaDTO;
import com.server.tourApiProject.weather.area.WeatherArea;
import com.server.tourApiProject.weather.area.WeatherAreaService;
import com.server.tourApiProject.weather.description.DescriptionRepository;
import com.server.tourApiProject.weather.fineDust.FineDustService;
import com.server.tourApiProject.weather.observation.WeatherObservation;
import com.server.tourApiProject.weather.observation.WeatherObservationService;
import com.server.tourApiProject.weather.observationalFit.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.server.tourApiProject.weather.common.CommonService.getObservationalFitDBBaseDate;

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

    // 날씨 상세 페이지용
    private static final Map<Double, String> effectMap = Map.of(
            0D, "많은 구름",
            1D, "나쁜 체감온도",
            2D, "밝은 달빛",
            3D, "나쁜 미세먼지",
            4D, "높은 강수확률",
            5D, "높은 광공해");

    // 관심지역 상세 페이지용
    private static final Map<Integer, String> InterestEffectMap = Map.of(
            0, "많은 구름",
            1, "나쁜 체감온도",
            2, "밝은 달빛",
            3, "나쁜 미세먼지",
            4, "높은 강수확률",
            5, "높은 광공해");

    public Mono<WeatherInfo> getWeatherInfo(AreaTimeDTO areaTime) {

//        log.info("Weather Request from App | {} ", areaTime);

        return Mono.zip(Mono.just(fineDustService.getFineDustMap(areaTime.getDate())),
                        getOpenWeather(areaTime.getLat(), areaTime.getLon()))
                .flatMap(zip -> {
                    Map<String, String> fineDustMap = zip.getT1();
                    OpenWeatherResponse openWeatherResponse = zip.getT2();

                    if (openWeatherResponse.getLat() == null) {
                        return Mono.just(WeatherInfo.builder().build());
                    }

                    // 미세먼지, 광공해 계산
                    String fineDust = null;
                    Double lightPollution = null;
                    if (areaTime.getAreaId() != null) { // 지역 case
                        WeatherArea area = weatherAreaService.getWeatherArea(areaTime.getAreaId());
                        if (Objects.equals(area.getSD(), "강원") || Objects.equals(area.getSD(), "경기")) {
                            fineDust = fineDustMap.getOrDefault(area.getSD2(), "보통");
                        } else {
                            fineDust = fineDustMap.getOrDefault(area.getSD(), "보통");
                        }
                        lightPollution = area.getLightPollution();
                    } else if (areaTime.getObservationId() != null) { // 관측지 case
                        WeatherObservation observation = weatherObservationService.getWeatherObservation(areaTime.getObservationId());
                        fineDust = fineDustMap.getOrDefault(observation.getFineDustAddress(), "보통");
                        lightPollution = observation.getLightPollution();
                    }

                    // 상세 날씨 계산
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
//                    detailWeather.setMoonAge(getMoonPhaseString(Double.valueOf(daily.getMoonPhase())));
                    detailWeather.setMoonAge(String.valueOf(daily.getMoonPhase())); // 월령 값을 단어가 아닌 수치로 변경해달라고 하여 수정함
                    detailWeather.setSunrise(getHHmm(daily.getSunrise()));
                    detailWeather.setSunset(getHHmm(daily.getSunset()));
                    detailWeather.setMoonrise(getHHmm(daily.getMoonrise()));
                    detailWeather.setMoonset(getHHmm(daily.getMoonset()));

                    // 시간별 관측적합도 계산
                    List<WeatherInfo.HourObservationalFit> hourList = new ArrayList<>();
                    Daily H_daily1 = openWeatherResponse.getDaily().get(0); // +0일
                    Daily H_daily2 = openWeatherResponse.getDaily().get(1); // +1일

                    double minObservationalFit = 0D; // 최소 관측적합도
                    double maxObservationalFit = 0D; // 최대 관측적합도
                    double mainEffect = 0D; // 관측적합도의 주요 원인
                    double avgObservationalFit = 0D; // 평균 관측적합도
                    int avgCount = 0;
                    int bestTime = 0; // 최고 관측적합도 시각
                    int sunset = getH(daily.getSunset()); // 일몰 시간
                    int sunrise = getH(daily.getSunrise()); // 일출 시간

                    int start = 0;
                    int finish = 0;
                    Integer hour = areaTime.getHour(); // 현재 시각
                    if (6 < hour && hour < 18) {
                        start = 18 - hour;
                        finish = 30 - hour;
                    } else {
                        if (hour >= 18) finish = 30 - hour;
                        if (hour <= 6) finish = 6 - hour;
                    }

                    for (int i = start; i <= finish; i++) {
                        Hourly H_hourly = openWeatherResponse.getHourly().get(i);
                        Integer dt = getH(H_hourly.getDt());
                        if ((dt >= 18 && dt < sunset + 2) || (dt <= 6 && dt > sunrise - 1)) continue;

                        avgCount++;
                        ObservationFitValue fitValue;

                        if (18 <= dt) { // 금일 18 ~ 23시 (6개)
                            fitValue = getObservationFit(
                                    H_hourly.getClouds(),
                                    H_hourly.getFeelsLike(),
                                    H_daily1.getMoonPhase(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                        } else { // 명일 0시 ~ 6시 (7개)
                            fitValue = getObservationFit(
                                    H_hourly.getClouds(),
                                    H_hourly.getFeelsLike(),
                                    H_daily2.getMoonPhase(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                        }

                        if (maxObservationalFit < fitValue.getObservationalFit()) {
                            maxObservationalFit = fitValue.getObservationalFit();
                            bestTime = dt;
                            mainEffect = fitValue.getTop1EffectIdx();
                        }
                        if (minObservationalFit > fitValue.getObservationalFit()) {
                            minObservationalFit = fitValue.getObservationalFit();
                        }
                        avgObservationalFit += fitValue.getObservationalFit();

                        hourList.add(WeatherInfo.HourObservationalFit.builder()
                                .hour(dt + "시")
                                .observationalFit(Math.round(fitValue.getObservationalFit()) + Const.Weather.PERCENT).build());
                    }

                    // 주간 예보 계산
                    List<WeatherInfo.DayObservationalFit> dayList = new ArrayList<>();
                    Map<Integer, String[]> dayDateMap = getDayDateMap();
                    for (int i = 0; i < 7; i++) {
                        Daily D_daily = openWeatherResponse.getDaily().get(i);
                        FeelsLike feelsLike = D_daily.getFeelsLike();

                        double observationalFit = getObservationFit(
                                D_daily.getClouds(),
                                Arrays.stream(new Double[]{feelsLike.getDay(), feelsLike.getNight(), feelsLike.getEve(), feelsLike.getMorn()}).max(Comparator.comparing(x -> x)).get(),
                                D_daily.getMoonPhase(),
                                fineDust,
                                Double.valueOf(D_daily.getPop()),
                                lightPollution).getObservationalFit();

                        WeatherInfo.DayObservationalFit dayInfo = WeatherInfo.DayObservationalFit.builder()
                                .day(dayDateMap.get(i)[0])
                                .date(dayDateMap.get(i)[1])
                                .observationalFit(Math.round(observationalFit) + Const.Weather.PERCENT).build();
                        if (i == 0)
                            dayInfo.setObservationalFit(Math.round(avgObservationalFit / avgCount) + Const.Weather.PERCENT);
                        dayList.add(dayInfo);
                    }

                    String[] todayComment = getTodayComment(bestTime, minObservationalFit, maxObservationalFit, mainEffect);

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
                }).doOnNext(weatherInfo -> {
                    // 관측지 최대 관측적합도 갱신
                    if (areaTime.getObservationId() != null) {
                        observationalFitRepository.findByDateAndObservationCode(getObservationalFitDBBaseDate(), areaTime.getObservationId())
                                .ifPresent(observationalFit -> {
                                    System.out.println("수정");
                                    observationalFit.setBestObservationalFit(Double.valueOf(weatherInfo.getBestObservationalFit()));
                                    observationalFitRepository.save(observationalFit);
                                });
                    }
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

    public String[] getTodayComment(int bestTime, double minObservationalFit, double maxObservationalFit, double mainEffect) {
        int min = (int) Math.round(minObservationalFit);
        int max = (int) Math.round(maxObservationalFit);

        if (min >= 85) {
            return new String[]{"오늘은 하루종일", "별 보기 최고에요"};
        } else if (max >= 70) {
            if(bestTime <= 6)
                return new String[]{"내일 0" + bestTime + "시가", "별 보기 가장 좋아요"};
            else
                return new String[]{"오늘 " + bestTime + "시가", "별 보기 가장 좋아요"};
        } else if (max >= 60) {
            if(bestTime <= 6)
                return new String[]{"내일 0" + bestTime + "시가", "별 보기 적당해요"};
            else
                return new String[]{"오늘 " + bestTime + "시가", "별 보기 적당해요"};
        } else if (max >= 40) {
            return new String[]{"오늘은 하루종일", "별 보기 조금 아쉬워요", effectMap.get(mainEffect)};
        } else {
            return new String[]{"오늘은 하루종일", "별 보기 어려워요", effectMap.get(mainEffect)};
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

    // unix UTC 을 HH:mm KST 로 바꾸는 메서드 (HH:mm)
    public String getHHmm(String unixTime) {
        Date unixHourMin = new Date(Long.parseLong(unixTime) * 1000L);
        SimpleDateFormat formatHHmm = new SimpleDateFormat("HH:mm");
        formatHHmm.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
        return formatHHmm.format(unixHourMin);
    }

    // unix UTC 을 HH:mm KST 로 바꾸는 메서드 (H)
    public Integer getH(String unixTime) {
        Date unixHourMin = new Date(Long.parseLong(unixTime) * 1000L);
        SimpleDateFormat formatH = new SimpleDateFormat("H");
        formatH.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
        return Integer.valueOf(formatH.format(unixHourMin));
    }

    public void setObservationFit(String date) {
        Map<String, String> fineDustMap = fineDustService.getFineDustMap(date);

        for (WeatherObservation observation : weatherObservationService.getAllObservation()) {
            String fineDust = fineDustMap.getOrDefault(observation.getFineDustAddress(), "보통");
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
                        Hourly hourly = openWeatherResponse.getHourly().get(i + 11);
                        if (i < 6) { // 금일 18 ~ 23시 (6개)
                            observationFitList[i] = getObservationFit(
                                    hourly.getClouds(),
                                    hourly.getFeelsLike(),
                                    daily1.getMoonPhase(),
                                    fineDust,
                                    Double.valueOf(hourly.getPop()),
                                    observation.getLightPollution()
                            ).getObservationalFit();
                        } else { // 명일 0시 ~ 6시 (7개)
                            observationFitList[i] = getObservationFit(
                                    hourly.getClouds(),
                                    hourly.getFeelsLike(),
                                    daily2.getMoonPhase(),
                                    fineDust,
                                    Double.valueOf(hourly.getPop()),
                                    observation.getLightPollution()
                            ).getObservationalFit();
                        }
                    }
                    double bestObservationFit = Arrays.stream(observationFitList).max().getAsDouble();
                    observationalFitRepository.save(ObservationalFit.builder()
                            .bestObservationalFit(bestObservationFit)
                            .observationCode(observation.getObservationId())
                            .date(date)
                            .build());
                })
                .onErrorResume(e -> Mono.empty())
//                .retryWhen(Retry.fixedDelay(1, Duration.ofSeconds(5)))
                .block();
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
                .onErrorReturn(ResponseEntity.ok(new OpenWeatherResponse()))
                .flatMap(response -> {
//                    log.info("HTTP Response for Open Weather ({}, {}) | {} | {}", lat, lon, response.getStatusCode(), response.getBody());
                    return Mono.just(Objects.requireNonNull(response.getBody()));
                });
//        return Mono.error(new IllegalStateException());
    }

    public Flux<String> getInterestAreaInfo(List<AreaTimeDTO> areaTimeList) {

        Map<String, String> fineDustMap = fineDustService.getFineDustMap(areaTimeList.get(0).getDate());

        return Flux.fromIterable(areaTimeList)
                .concatMap(areaTime ->
                        getOpenWeather(areaTime.getLat(), areaTime.getLon())
                                .map(openWeatherResponse -> {

                                    if (openWeatherResponse.getLat() == null) {
                                        return "-1";
                                    }

                                    String fineDust = null;
                                    Double lightPollution = null;

                                    // 미세먼지, 광공해 계산
                                    if (areaTime.getAreaId() != null) {
                                        WeatherArea area = weatherAreaService.getWeatherArea(areaTime.getAreaId());
                                        if (Objects.equals(area.getSD(), "강원") || Objects.equals(area.getSD(), "경기")) {
                                            fineDust = fineDustMap.getOrDefault(area.getSD2(), "보통");
                                        } else {
                                            fineDust = fineDustMap.getOrDefault(area.getSD(), "보통");
                                        }
                                        lightPollution = area.getLightPollution();
                                    } else if (areaTime.getObservationId() != null) {
                                        WeatherObservation observation = weatherObservationService.getWeatherObservation(areaTime.getObservationId());
                                        fineDust = fineDustMap.getOrDefault(observation.getFineDustAddress(), "보통");
                                        lightPollution = observation.getLightPollution();
                                    }

                                    Daily H_daily1 = openWeatherResponse.getDaily().get(0); // +0일
                                    Daily H_daily2 = openWeatherResponse.getDaily().get(1); // +1일

                                    double minObservationalFit = 0D; // 최소 관측적합도
                                    double maxObservationalFit = 0D; // 최대 관측적합도
                                    int sunset = getH(H_daily1.getSunset()); // 일몰 시간
                                    int sunrise = getH(H_daily1.getSunrise()); // 일출 시간

                                    int start = 0;
                                    int finish = 0;
                                    Integer hour = areaTime.getHour(); // 현재 시각
                                    if (6 < hour && hour < 18) {
                                        start = 18 - hour;
                                        finish = 30 - hour;
                                    } else {
                                        if (hour >= 18) finish = 30 - hour;
                                        if (hour <= 6) finish = 6 - hour;
                                    }

                                    for (int i = start; i <= finish; i++) {
                                        Hourly H_hourly = openWeatherResponse.getHourly().get(i);
                                        Integer dt = getH(H_hourly.getDt());
                                        if ((dt >= 18 && dt < sunset + 2) || (dt <= 6 && dt > sunrise - 1)) continue;

                                        double observationalFit;
                                        if (i < 6) { // 금일 18 ~ 23시 (6개)
                                            observationalFit = getObservationFit(
                                                    H_hourly.getClouds(),
                                                    H_hourly.getFeelsLike(),
                                                    H_daily1.getMoonPhase(),
                                                    fineDust,
                                                    Double.valueOf(H_hourly.getPop()),
                                                    lightPollution
                                            ).getObservationalFit();
                                        } else { // 명일 0시 ~ 6시 (7개)
                                            observationalFit = getObservationFit(
                                                    H_hourly.getClouds(),
                                                    H_hourly.getFeelsLike(),
                                                    H_daily2.getMoonPhase(),
                                                    fineDust,
                                                    Double.valueOf(H_hourly.getPop()),
                                                    lightPollution
                                            ).getObservationalFit();
                                        }
                                        if (maxObservationalFit < observationalFit) {
                                            maxObservationalFit = observationalFit;
                                        }
                                        if (minObservationalFit > observationalFit) {
                                            minObservationalFit = observationalFit;
                                        }
                                    }
                                    return String.valueOf(Math.round(maxObservationalFit));
                                }).subscribeOn(Schedulers.parallel()));
    }

    public String getMainComment(double minObservationalFit, double maxObservationalFit, int bestTime) {
        int min = (int) Math.round(minObservationalFit);
        int max = (int) Math.round(maxObservationalFit);

        String prefix = "관측적합도 최대 " + max + "%로 ";

        String timeComment;
        if (bestTime <= 6) {
            timeComment = "추천 관측 시간은 내일 0" + bestTime + "시에요.";
        } else {
            timeComment = "추천 관측 시간은 오늘 " + bestTime + "시에요.";
        }

        if (min >= 85) {
            return prefix + "별 보기 최고의 날이네요!\n" + timeComment;
        } else if (max >= 70) {
            return prefix + "별 보기 좋은 날이네요!\n" + timeComment;
        } else if (max >= 60) {
            return prefix + "별 보기 괜찮은 날이네요!\n" + timeComment;
        } else if (max >= 40) {
            return prefix + "오늘은 별 보기 조금 아쉽네요.";
        } else {
            return prefix + "오늘은 별을 보기 어려워요.";
        }
    }

    public ObservationFitValue getObservationFit(Double clouds, Double feelLike, Double moonPhase,
                                                 String fineDust, Double pop, Double lightPollution) {
        double[] values = {getCloudsValue(clouds),
                getFeelLikeValue(feelLike),
                getMoonPhaseValue(moonPhase),
                getFineDustValue(fineDust),
                getPopValue(pop),
                getLightPollutionValue(lightPollution)};

        double minValue = Arrays.stream(values)
                .min()
                .orElseThrow(() -> new IllegalStateException("Array is empty."));
        double otherSumValue = Math.round((Arrays.stream(values).sum() - minValue) / 6.0 * 100) / 100.0;
        double total = total(minValue, otherSumValue);
        int idx = minValueIdx(values);

        return ObservationFitValue.builder()
                .observationalFit(total)
                .top1EffectIdx(idx)
                .build();
    }

    public InterestObservationFitValue getInterestObservationFit(Double clouds, Double feelLike, Double moonPhase,
                                                                 String fineDust, Double pop, Double lightPollution) {
        double[] values = {getCloudsValue(clouds),
                getFeelLikeValue(feelLike),
                getMoonPhaseValue(moonPhase),
                getFineDustValue(fineDust),
                getPopValue(pop),
                getLightPollutionValue(lightPollution)};

        double minValue = Arrays.stream(values)
                .min()
                .orElseThrow(() -> new IllegalStateException("Array is empty."));
        double otherSumValue = Math.round((Arrays.stream(values).sum() - minValue) / 6.0 * 100) / 100.0;
        double total = total(minValue, otherSumValue);
        int[] idxArray = min3ValueIdx(values);

        return InterestObservationFitValue.builder()
                .observationalFit(total)
                .top3EffectIdx(idxArray)
                .moonPhase(moonPhase)
                .build();
    }

    public double getCloudsValue(Double clouds) {
        return Math.round(100 * (-(1 / (-0.25 * (clouds / 100 - 2.7)) - 1.48148)) * 100) / 100.0;
    }

    public double getFeelLikeValue(Double feelLike) {
        double feelLikeValue;
        if (feelLike < 18) {
            feelLikeValue = -0.008 * Math.pow((feelLike - 18), 2);
        } else {
            feelLikeValue = -0.09 * Math.pow((feelLike - 18), 2);
        }
        return Math.round(feelLikeValue * 100) / 100.0;
    }

    public double getMoonPhaseValue(Double moonPhase) {
        double moonPhaseValue;
        if (moonPhase <= 0.5) {
            moonPhaseValue = -Math.round(((8 * Math.pow(moonPhase, 3.46)) / 0.727 * 100) * 100) / 100.0;
        } else if (moonPhase <= 0.5609) {
            moonPhaseValue = -Math.round(((-75 * Math.pow(moonPhase - 0.5, 2) + 0.727) / 0.727 * 100) * 100) / 100.0;
        } else {
            moonPhaseValue = -Math.round(((1 / (5.6 * Math.pow(moonPhase + 0.3493, 10)) - 0.0089) / 0.727 * 100) * 100) / 100.0;
        }
        return moonPhaseValue;
    }

    public double getFineDustValue(String fineDust) {
        double fineDustValue;
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
        return fineDustValue;
    }

    public double getPopValue(Double pop) {
        return Math.round(100 * (-(1 / (-(1.2) * (pop / 100 - 1.5)) - 0.55556)) * 100) / 100.0;
    }

    public double getLightPollutionValue(Double lightPollution) {
        double lightPollutionValue;
        if (lightPollution < 28.928) {
            lightPollutionValue = -(1 / (-(0.001) * (lightPollution - 48)) - 20.833);
        } else if (lightPollution < 77.53) {
            lightPollutionValue = -(1 / (-(0.0001) * (lightPollution + 52)) + 155);
        } else if (lightPollution < 88.674) {
            lightPollutionValue = -(1 / (-(0.001) * (lightPollution - 110)) + 47);
        } else {
            lightPollutionValue = -(1 / (-(0.01) * (lightPollution - 71)) + 100);
        }
        return Math.round(lightPollutionValue * 100) / 100.0;
    }

    public int minValueIdx(double[] values) {
        double min = values[0];
        int idx = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
                idx = i;
            }
        }
        return idx;
    }

    // 가장 작은 3개 double 값의 인덱스를 찾는 메서드
    public int[] min3ValueIdx(double[] values) {
        int[] idxArray = new int[3];

        double[] sortedValues = Arrays.copyOf(values, values.length);
        Arrays.sort(sortedValues);

        for (int i = 0; i < 3; i++) {
            double minValue = sortedValues[i];
            idxArray[i] = indexOf(values, minValue);
        }
        return idxArray;
    }

    private int indexOf(double[] array, double value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public double total(Double minValue, Double otherSumValue) {
        double value = 100 + (minValue + otherSumValue * 0.5);
        return (value > 0) ? (Math.round(value * 100) / 100.0) : 0;
    }

    public Mono<MainInfo> getNearestAreaWeatherInfo(NearestAreaDTO nearestAreaDTO) {
        WeatherArea nearestArea = weatherAreaService.getNearestArea(nearestAreaDTO);
        Long areaId = nearestArea.getAreaId();

        String EMD;
        if (nearestAreaDTO.getSgg().equals("세종")) {
            EMD = nearestArea.getEMD1();
        } else {
            EMD = nearestArea.getEMD2();
            if (nearestArea.getEMD3() != null) EMD += " " + nearestArea.getEMD3();
        }
        String location = EMD;

        return Mono.zip(Mono.just(fineDustService.getFineDustMap(nearestAreaDTO.getDate())),
                        getOpenWeather(nearestAreaDTO.getLatitude(), nearestAreaDTO.getLongitude()))
                .flatMap(zip -> {
                    Map<String, String> fineDustMap = zip.getT1();
                    OpenWeatherResponse openWeatherResponse = zip.getT2();

                    // 미세먼지, 광공해 계산
                    String fineDust;
                    Double lightPollution;
                    WeatherArea area = weatherAreaService.getWeatherArea(areaId);
                    if (Objects.equals(area.getSD(), "강원") || Objects.equals(area.getSD(), "경기")) {
                        fineDust = fineDustMap.getOrDefault(area.getSD2(), "보통");
                    } else {
                        fineDust = fineDustMap.getOrDefault(area.getSD(), "보통");
                    }
                    lightPollution = area.getLightPollution();

                    Hourly hourly = openWeatherResponse.getHourly().get(0); // 현재 Hour 기준 날씨
                    Daily daily = openWeatherResponse.getDaily().get(0); // 현재 Date 기준 날씨

                    Daily H_daily1 = openWeatherResponse.getDaily().get(0); // +0일
                    Daily H_daily2 = openWeatherResponse.getDaily().get(1); // +1일

                    double maxObservationalFit = 0D; // 최대 관측적합도
                    double avgObservationalFit = 0D; // 평균 관측적합도
                    int avgCount = 0;
                    int bestTime = 0; // 최고 관측적합도 시각
                    int[] mainEffectArray = new int[3]; // 관측적합도의 주요 원인
                    double mainEffectMoonAgeValue = 0D; // 최대 관측적합도의 최고 저해요인이 월령일 때의 월령 값
                    int sunset = getH(daily.getSunset()); // 일몰 시간
                    int sunrise = getH(daily.getSunrise()); // 일출 시간

                    int start = 0;
                    int finish = 0;
                    Integer hour = nearestAreaDTO.getHour(); // 현재 시각
                    if (6 < hour && hour < 18) {
                        start = 18 - hour;
                        finish = 30 - hour;
                    } else {
                        if (hour >= 18) finish = 30 - hour;
                        if (hour <= 6) finish = 6 - hour;
                    }

                    for (int i = start; i <= finish; i++) {
                        Hourly H_hourly = openWeatherResponse.getHourly().get(i);
                        Integer dt = getH(H_hourly.getDt());
                        if ((dt >= 18 && dt < sunset + 2) || (dt <= 6 && dt > sunrise - 1)) continue;

                        avgCount++;
                        InterestObservationFitValue fitValue;

                        if (i < 6) { // 금일 18 ~ 23시 (6개)
                            fitValue = getInterestObservationFit(
                                    H_hourly.getClouds(),
                                    H_hourly.getFeelsLike(),
                                    H_daily1.getMoonPhase(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                        } else { // 명일 0시 ~ 6시 (7개)
                            fitValue = getInterestObservationFit(
                                    H_hourly.getClouds(),
                                    H_hourly.getFeelsLike(),
                                    H_daily2.getMoonPhase(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                        }

                        if (maxObservationalFit < fitValue.getObservationalFit()) {
                            maxObservationalFit = fitValue.getObservationalFit();
                            bestTime = dt;
                            mainEffectArray = fitValue.getTop3EffectIdx();
                            mainEffectMoonAgeValue = fitValue.getMoonPhase();
                        }
                        avgObservationalFit += fitValue.getObservationalFit();
                    }

                    String bestTimeForReport;
                    if (bestTime <= 6) bestTimeForReport = "내일 0" + bestTime + "시";
                    else bestTimeForReport = "오늘 " + bestTime + "시";

                    return Mono.just(MainInfo.builder()
                            .location(location)
                            .comment(generateWeatherReport(getDescription(hourly.getWeather().get(0).getId()),
                                    Math.round(daily.getTemp().getMin()),
                                    Math.round(daily.getTemp().getMax()),
                                    daily.getMoonPhase(),
                                    hourly.getClouds(),
                                    Math.round(Double.parseDouble(hourly.getPop()) * 100),
                                    hourly.getWindSpeed(),
                                    fineDust,
                                    maxObservationalFit,
                                    avgObservationalFit / avgCount,
                                    bestTimeForReport,
                                    mainEffectArray,
                                    mainEffectMoonAgeValue
                            ))
                            .regionId(areaId)
                            .build());
                });
    }

    /**
     * 관심지역 상세 페이지 날씨 조회
     */
    public Mono<InterestAreaDetailWeatherInfo> getInterestAreaDetailInfo(AreaTimeDTO areaTime) {

        return Mono.zip(Mono.just(fineDustService.getFineDustMap(areaTime.getDate())),
                        getOpenWeather(areaTime.getLat(), areaTime.getLon()))
                .flatMap(zip -> {
                    Map<String, String> fineDustMap = zip.getT1();
                    OpenWeatherResponse openWeatherResponse = zip.getT2();

                    if (openWeatherResponse.getLat() == null) {
                        return Mono.just(InterestAreaDetailWeatherInfo.builder().build());
                    }

                    // 미세먼지, 광공해 계산
                    String fineDust = null;
                    Double lightPollution = null;
                    if (areaTime.getAreaId() != null) {
                        WeatherArea area = weatherAreaService.getWeatherArea(areaTime.getAreaId());
                        if (Objects.equals(area.getSD(), "강원") || Objects.equals(area.getSD(), "경기")) {
                            fineDust = fineDustMap.getOrDefault(area.getSD2(), "보통");
                        } else {
                            fineDust = fineDustMap.getOrDefault(area.getSD(), "보통");
                        }
                        lightPollution = area.getLightPollution();
                    } else if (areaTime.getObservationId() != null) {
                        WeatherObservation observation = weatherObservationService.getWeatherObservation(areaTime.getObservationId());
                        fineDust = fineDustMap.getOrDefault(observation.getFineDustAddress(), "보통");
                        lightPollution = observation.getLightPollution();
                    }

                    // 시간별 관측적합도 계산
                    Hourly hourly = openWeatherResponse.getHourly().get(0); // 현재 Hour 기준 날씨
                    Daily daily = openWeatherResponse.getDaily().get(0); // 현재 Date 기준 날씨

                    Daily H_daily1 = openWeatherResponse.getDaily().get(0); // +0일
                    Daily H_daily2 = openWeatherResponse.getDaily().get(1); // +1일

                    double maxObservationalFit = 0D; // 최대 관측적합도
                    double avgObservationalFit = 0D; // 평균 관측적합도
                    int avgCount = 0;
                    int[] mainEffectArray = new int[3]; // 관측적합도의 주요 원인
                    double mainEffectMoonAgeValue = 0D; // 최대 관측적합도의 최고 저해요인이 월령일 때의 월령 값
                    int bestTime = 0; // 최고 관측적합도 시각
                    int sunset = getH(daily.getSunset()); // 일몰 시간
                    int sunrise = getH(daily.getSunrise()); // 일출 시간

                    int start = 0;
                    int finish = 0;
                    Integer hour = areaTime.getHour(); // 현재 시각
                    if (6 < hour && hour < 18) {
                        start = 18 - hour;
                        finish = 30 - hour;
                    } else {
                        if (hour >= 18) finish = 30 - hour;
                        if (hour <= 6) finish = 6 - hour;
                    }

                    for (int i = start; i <= finish; i++) {
                        Hourly H_hourly = openWeatherResponse.getHourly().get(i);
                        Integer dt = getH(H_hourly.getDt());
                        if ((dt >= 18 && dt < sunset + 2) || (dt <= 6 && dt > sunrise - 1)) continue;

                        avgCount++;
                        InterestObservationFitValue fitValue;
                        if (i < 6) { // 금일 18 ~ 23시 (6개)
                            fitValue = getInterestObservationFit(
                                    H_hourly.getClouds(),
                                    H_hourly.getFeelsLike(),
                                    H_daily1.getMoonPhase(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                        } else { // 명일 0시 ~ 6시 (7개)
                            fitValue = getInterestObservationFit(
                                    H_hourly.getClouds(),
                                    H_hourly.getFeelsLike(),
                                    H_daily2.getMoonPhase(),
                                    fineDust,
                                    Double.valueOf(H_hourly.getPop()),
                                    lightPollution
                            );
                        }
                        if (maxObservationalFit < fitValue.getObservationalFit()) {
                            maxObservationalFit = fitValue.getObservationalFit();
                            bestTime = dt;
                            mainEffectArray = fitValue.getTop3EffectIdx();
                            mainEffectMoonAgeValue = fitValue.getMoonPhase();
                        }
                        avgObservationalFit += fitValue.getObservationalFit();
                    }

                    String bestTimeForReport;
                    if (bestTime <= 6) bestTimeForReport = "내일 0" + bestTime + "시";
                    else bestTimeForReport = "오늘 " + bestTime + "시";

                    InterestAreaDetailWeatherInfo interestAreaDetailWeatherInfo = InterestAreaDetailWeatherInfo.builder()
                            .bestDay(bestTime <= 6 ? "내일" : "오늘")
                            .bestHour(bestTime)
                            .bestObservationalFit((int) Math.round(maxObservationalFit))
                            .weatherReport(generateWeatherReport(getDescription(hourly.getWeather().get(0).getId()),
                                    Math.round(daily.getTemp().getMin()),
                                    Math.round(daily.getTemp().getMax()),
                                    daily.getMoonPhase(),
                                    hourly.getClouds(),
                                    Math.round(Double.parseDouble(hourly.getPop()) * 100),
                                    hourly.getWindSpeed(),
                                    fineDust,
                                    maxObservationalFit,
                                    avgObservationalFit / avgCount,
                                    bestTimeForReport,
                                    mainEffectArray,
                                    mainEffectMoonAgeValue
                            ))
                            .build();

                    return Mono.just(interestAreaDetailWeatherInfo);
                });
    }

    /**
     * 관심지역 상세페이지 날씨 요약 레포트 생성
     *
     * @param weatherText            : 날씨(맑음, 눈 등등)
     * @param tempLowest             : 최저 기온
     * @param tempHighest            : 최고 기온
     * @param moonAge                : 월령
     * @param cloud                  : 구름양
     * @param rainfallProbability    : 강수확률
     * @param windSpeed              : 풍속
     * @param fineDust               : 미세먼지
     * @param maxObservationalFit    : 최고 관측적합도
     * @param avgObservationalFit    : 평균 관측적합도
     * @param bestTime               : 추천 관측시간 (오늘 18시, 내일 02시)
     * @param mainEffectArray        : 저해요인
     * @param mainEffectMoonAgeValue : 최대 관측적합도의 최고 저해요인이 월령일 때의 월령 값
     * @return 날씨 요약 레포트
     */
    private String generateWeatherReport(String weatherText, long tempLowest, long tempHighest,
                                         double moonAge, double cloud, long rainfallProbability, double windSpeed, String fineDust,
                                         double maxObservationalFit, double avgObservationalFit, String bestTime,
                                         int[] mainEffectArray, double mainEffectMoonAgeValue) {

        String s1 = "오늘 날씨는 " + weatherText + ", 기온은 최저 " + tempLowest + "°C, 최고 " + tempHighest + "°C에요. ";
        String s2 = "";
        String s3 = "";
        String s4 = "";
        String s5 = "";

        if (maxObservationalFit >= 60D) {
            if (moonAge >= 0.94D || moonAge <= 0.1D) {
                s2 = "월령이 매우 낮고 ";
            } else {
                if (moonAge >= 0.9D || moonAge <= 0.14D) {
                    s2 = "월령이 낮은 편이고 ";
                } else {
                    s2 = "달빛이 너무 강하지 않고 ";
                }
            }

            if (cloud >= 0.15D) {
                s3 = "구름이 거의 없어 ";
            } else {
                s3 = "구름이 적어 ";
            }

            if (avgObservationalFit >= 85D) {
                s4 = "별 보기 최고에요! ";
            } else {
                if (maxObservationalFit >= 85D) {
                    s4 = "별 보기 아주 좋아요! 추천 관측시간은 " + bestTime + "에요. ";
                } else {
                    if (maxObservationalFit >= 70D) {
                        s4 = "별 보기 좋아요. 하지만 달빛과 구름이 적은 시간을 잘 확인하세요. 추천 관측 시간은 " + bestTime + "에요. ";
                    } else {
                        s4 = "별 보기 적당해요. " + InterestEffectMap.get(mainEffectArray[0]) + "에 유의하세요. 추천 관측 시간은 " + bestTime + "에요. ";
                    }
                }
            }
        } else {
            if (maxObservationalFit >= 40D) {
                s2 = InterestEffectMap.get(mainEffectArray[0]) + "(으)로 별 보기 조금 아쉬워요. ";
            } else {
                s2 = InterestEffectMap.get(mainEffectArray[0]) + ", "
                        + InterestEffectMap.get(mainEffectArray[1]) + ", "
                        + InterestEffectMap.get(mainEffectArray[2]) + "(으)로 별 보기 어려워요. ";
            }

            if (weatherText.contains("맑음")) {
                if (0.54D >= mainEffectMoonAgeValue && mainEffectMoonAgeValue >= 0.46D) {
                    s3 = "하지만 밝은 '보름달'을 볼 수 있는 날이에요! ";
                    s4 = "달빛이 줄어드는 " + (int) ((1 - mainEffectMoonAgeValue) / 0.04) + "일 후에 별을 보는 것을 추천해요. ";
                } else {
                    if (0.62D >= mainEffectMoonAgeValue && mainEffectMoonAgeValue >= 0.38D) {
                        s3 = "하지만 밝은 달을 보기에는 좋은 날이에요. ";
                        s4 = "달빛이 줄어드는 " + (int) ((1 - mainEffectMoonAgeValue) / 0.04) + "일 후에 별을 보는 것을 추천해요. ";
                    } else {
                        s3 = "날씨가 얼른 나아지면 좋겠어요. ";
                    }
                }
            } else {
                if (mainEffectArray[0] == 5) {
                    s3 = "광공해가 적은 주변 지역으로 별을 보러 떠나보는 것은 어떨까요? ";
                } else {
                    s3 = "날씨가 얼른 나아지면 좋겠어요. ";
                }
            }
        }

        if (weatherText.contains("눈")) {
            s5 = "눈 소식에 유의하세요!";
        } else {
            if (rainfallProbability >= 50) {
                s5 = "비 소식이 있어요. 외출할 때 우산을 챙겨가세요.";
            } else {
                if (windSpeed >= 3.4D) {
                    s5 = "바람이 많이 불 수 있으니 조심하세요!";
                } else {
                    if (fineDust.equals("나쁨")) {
                        s5 = "미세먼지가 '나쁨'이에요. 마스크를 챙겨가세요.";
                    }
                }
            }
        }
        return s1 + s2 + s3 + s4 + s5;
    }

    public Mono<OpenWeatherResponse> getOpenWeather2(Double lat, Double lon) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8081/onecall");

        return webClient.get()
                .uri(uriBuilder.build().toUri())
                .retrieve()
                .toEntity(OpenWeatherResponse.class)
                .onErrorReturn(ResponseEntity.ok(new OpenWeatherResponse()))
                .flatMap(response -> {
                    log.info("HTTP Response for Open Weather ({}, {}) | {} | {}", lat, lon, response.getStatusCode(), response.getBody());
                    return Mono.just(Objects.requireNonNull(response.getBody()));
                });
    }
}


