package com.server.tourApiProject.searchFirst;

import com.server.tourApiProject.observation.observeImage.ObserveImage;
import com.server.tourApiProject.observation.observeImage.ObserveImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SearchFirstService {

    private final SearchFirstRepository searchFirstRepository;
    private final ObserveImageRepository observeImageRepository;

    public List<SearchFirstParams> getSearchFirst(String typeName) {
        List<SearchFirst> list = searchFirstRepository.findByTypeName(typeName);
        List<SearchFirstParams> result = new ArrayList<>();

        for(SearchFirst searchFirst : list){
            SearchFirstParams params = new SearchFirstParams();
            params.setObservationId(searchFirst.getObservationId());
            params.setObservationName(searchFirst.getObservationName());

            if (!observeImageRepository.findByObservationId(params.getObservationId()).isEmpty()) {
                ObserveImage observeImage = observeImageRepository.findByObservationId(params.getObservationId()).get(0);
                params.setObservationImage(observeImage.getImage());
            }

            result.add(params);
        }

        return result;
    }
}
