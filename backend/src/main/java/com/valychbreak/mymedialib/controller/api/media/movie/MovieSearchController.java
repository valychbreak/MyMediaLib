package com.valychbreak.mymedialib.controller.api.media.movie;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.controller.api.media.MediaController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.media.movie.MovieSearchService;
import com.valychbreak.mymedialib.services.utils.SearchParamsBuilder;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MovieSearchController extends MediaController {

    private MovieSearchService movieSearchService;

    @Autowired
    public MovieSearchController(MovieSearchService movieSearchService) {
        this.movieSearchService = movieSearchService;
    }

    @RequestMapping(value = "/movie/search", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<SearchResult<MediaFullDetails>> movieSearch(@RequestParam(value = "q") String searchTerm,
                                                                              @RequestParam(value = "year", required = false) Integer year,
                                                                              @RequestParam(value = "p", required = false) Integer page
    ) throws OMDBException, IOException {

        SearchParamsBuilder searchParamsBuilder = new SearchParamsBuilder();
        searchParamsBuilder.withQuery(searchTerm).withPage(page);

        SearchResult<MediaFullDetails> searchResult = movieSearchService.search(searchParamsBuilder.build());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
