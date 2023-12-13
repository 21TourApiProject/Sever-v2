package com.server.tourApiProject.interestArea;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestAreaWeatherDTO {

    public Long regionId;
    public String regionName;
    public Integer regionType;
    public String regionImage;
    public String bestDay;
    public Integer bestHour;
    public Integer bestObservationalFit;
    public String weatherReport;
    public Double latitude;
    public Double longitude;
}
