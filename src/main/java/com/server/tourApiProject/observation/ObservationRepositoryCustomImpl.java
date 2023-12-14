package com.server.tourApiProject.observation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.tourApiProject.observation.model.ObservationSimpleParams;
import com.server.tourApiProject.observation.model.QObservationSimpleParams;
import com.server.tourApiProject.observation.observeImage.QObserveImage;
import com.server.tourApiProject.weather.observationalFit.QObservationalFit;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ObservationRepositoryCustomImpl implements ObservationRepositoryCustom{
    private final JPAQueryFactory query;
    QObservation observation = QObservation.observation;
    QObserveImage observeImage = QObserveImage.observeImage;
    QObservationalFit observationalFit = QObservationalFit.observationalFit;

    @Override
    public List<ObservationSimpleParams> findObservationSimpleByIdList(List<Long> observationIds, String date) {
        return query
                .select(
                new QObservationSimpleParams(observation.observationId, observeImage.image.max(),
                observation.observationName, observation.address,observation.intro, observation.longitude,
                observation.latitude, observation.light, observationalFit.bestObservationalFit, observation.saved))
                .from(observation)
                .leftJoin(observation.observeImages, observeImage)
                .groupBy(observationalFit.bestObservationalFit, observation.observationId)
                .join(observationalFit).on(observation.observationId.eq(observationalFit.observationCode),observationalFit.date.eq(date))
                .where(observation.observationId.in(observationIds))
                .orderBy(observationalFit.bestObservationalFit.desc())
                .fetch();
    }
}
