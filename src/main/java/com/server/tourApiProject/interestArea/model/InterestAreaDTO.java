package com.server.tourApiProject.interestArea.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestAreaDTO {

    public Long regionId;
    public String regionName;
    public Integer regionType;
    public String regionImage;
    public String observationalFit; // 반올림한 수치
}
