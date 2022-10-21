package com.server.tourApiProject.search;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    List<Long> areaCodeList;
    List<Long> hashTagIdList;

    public List<Long> getAreaCodeList() {
        return areaCodeList;
    }

    public List<Long> getHashTagIdList() {
        return hashTagIdList;
    }
}
