package com.server.tourApiProject.interestArea.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestAreaDetailDTO {

    public Long regionId;
    public String regionName;
    public Integer regionType;
    public String regionImage;
    public Double latitude;
    public Double longitude;
    public InterestAreaDetailWeatherInfo interestAreaDetailWeatherInfo;
}
