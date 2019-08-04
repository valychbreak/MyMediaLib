package com.valychbreak.mymedialib.services.media;

import com.uwetrottmann.tmdb2.entities.Media;
import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.utils.SearchParams;
import com.valychbreak.mymedialib.services.utils.SearchParamsBuilder;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TmdbMediaSearchService2MethodsComparisonIntegrationTest {

    @Autowired
    private TmdbMediaSearchService mediaSearchService;

    @Test
    public void fullMethodPerformanceTest() throws IOException {
        long initialTime = System.currentTimeMillis();
        SearchParams batman = new SearchParamsBuilder().withQuery("batman").withPage(1).withItemsPerPage(20).build();
        SearchResult<MediaFullDetails> searchResult = mediaSearchService.search(batman);
        System.out.println("It took " + (System.currentTimeMillis() - initialTime) + " to find " + searchResult.getItems().size() + " media with full details");
    }

    @Test
    public void basicMethodPerformanceTest() throws IOException {
        long initialTime = System.currentTimeMillis();
        SearchParams batman = new SearchParamsBuilder().withQuery("batman").withPage(1).withItemsPerPage(20).build();
        SearchResult<Media> searchResult = mediaSearchService.searchBasic(batman);
        System.out.println("It took " + (System.currentTimeMillis() - initialTime) + " to find " + searchResult.getItems().size() + " media with full details");
    }
}