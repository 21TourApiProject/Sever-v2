package com.server.tourApiProject.touristPoint.area;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaFilterParams {
    Long id;
    String name;
    String category = "AREA";

    public interface AreaFilter {
        Long getId();

        String getName();

    }
}
