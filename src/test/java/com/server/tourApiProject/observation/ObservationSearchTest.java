package com.server.tourApiProject.observation;

import com.server.tourApiProject.search.Filter;
import com.server.tourApiProject.search.SearchParams1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ObservationSearchTest {

    @Autowired
    ObservationService observationService;

    @Test
    void searchTest(){
        Filter f = new Filter();
        String searchKey = "";
        List<SearchParams1> result = observationService.getObservationWithFilter(f, searchKey);
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

}
