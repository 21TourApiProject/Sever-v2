package com.server.tourApiProject.bigPost.post;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class PostContentsParams {
    private Long itemId;
    private String thumbnail;
    private String title;
    private Long observationId;
    private String observationName;
    private String observationCustomName;
    private String contents;

    @QueryProjection
    public PostContentsParams(Long itemId, String thumbnail, String title, Long observationId, String observationName, String observationCustomName, String contents) {
        this.itemId = itemId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.observationId = observationId;
        this.observationName = observationName;
        this.observationCustomName = observationCustomName;
        this.contents = contents;
    }
}
