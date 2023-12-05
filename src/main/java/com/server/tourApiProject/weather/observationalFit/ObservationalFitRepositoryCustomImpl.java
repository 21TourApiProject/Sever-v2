package com.server.tourApiProject.weather.observationalFit;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ObservationalFitRepositoryCustomImpl implements ObservationalFitRepositoryCustom{

    private final JPAQueryFactory query;
    QObservationalFit observationalFit = QObservationalFit.observationalFit;

    @Override
    public List<Long> getObservationIdsByBestFit(String date) {
        return query
                .select(observationalFit.observationCode)
                .from(observationalFit)
                .where(observationalFit.date.eq(date))
                .orderBy(observationalFit.bestObservationalFit.desc())
                .limit(10)
                .fetch();
    }
}
