package com.server.tourApiProject.bigPost.post;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostContentsParams> getLatestContentsWithSize(int size);

    List<PostContentsParams> getObservationPostWithSize(int size, Long observationId);
}
