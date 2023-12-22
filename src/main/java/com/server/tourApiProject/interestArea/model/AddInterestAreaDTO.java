package com.server.tourApiProject.interestArea.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddInterestAreaDTO {

    private Long userId;
    private Long regionId;
    private String regionName;
    private Integer regionType;
}
