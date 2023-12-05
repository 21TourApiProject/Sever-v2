package com.server.tourApiProject.interestArea;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestAreaParams {

    private Long userId;

    private Long observationId;

    private String observationName;
}
