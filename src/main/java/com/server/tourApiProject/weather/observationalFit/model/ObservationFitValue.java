package com.server.tourApiProject.weather.observationalFit.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObservationFitValue {

    double observationalFit; // 관측적합도
    int top1EffectIdx; // 관측적합도의 최고 저해 요인 인덱스
}
