package com.server.tourApiProject.bigPost.post;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.tourApiProject.bigPost.postImage.QPostImage;
import com.server.tourApiProject.observation.QObservation;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{
    private final JPAQueryFactory query;
    QObservation observation = QObservation.observation;
    QPost post = QPost.post;
    QPostImage postImage = QPostImage.postImage;

    @Override
    public List<PostContentsParams> getLatestContentsWithSize(int size) {

        return query.select(new QPostContentsParams(post.postId,postImage.imageName.max(), post.postTitle, post.observationId,
                        observation.observationName, post.optionObservation, post.postContent))
                .from(post)
                .leftJoin(post.observation, observation)
                .leftJoin(post.postImages, postImage)
                .groupBy(post.postId)
                .orderBy(post.postId.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<PostContentsParams> getObservationPostWithSize(int size, Long observationId) {

        return query.select(new QPostContentsParams(post.postId,postImage.imageName.max(), post.postTitle, post.observationId,
                        observation.observationName, post.optionObservation, post.postContent))
                .from(post)
                .leftJoin(post.observation, observation)
                .leftJoin(post.postImages, postImage)
                .where(post.observationId.eq(observationId))
                .groupBy(post.postId)
                .orderBy(post.postId.desc())
                .limit(size)
                .fetch();
    }
}
