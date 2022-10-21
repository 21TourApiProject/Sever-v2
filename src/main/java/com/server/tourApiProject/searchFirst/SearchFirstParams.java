package com.server.tourApiProject.searchFirst;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchFirstParams {

    private Long observationId;

    private String  observationName;

    private String observationImage;
}
