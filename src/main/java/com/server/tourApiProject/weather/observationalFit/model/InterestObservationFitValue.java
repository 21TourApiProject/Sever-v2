package com.server.tourApiProject.weather.observationalFit.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestObservationFitValue {

    double observationalFit; // 관측적합도
    int[] top3EffectIdx; // 관측적합도의 상위 저해 요인 3개 인덱스 array
    double moonPhase; // 최대 관측적합도의 최고 저해요인이 월령일 때의 월령 값
}
