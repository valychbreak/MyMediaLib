package com.valychbreak.mymedialib.controller.api.media.tvshow;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.controller.api.media.MediaController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.media.MediaSearchService;
import com.valychbreak.mymedialib.services.media.tvshow.TvShowSearchService;
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
public class TvShowSearchController extends MediaController {

    private TvShowSearchService tvShowSearchService;

    @Autowired
    public TvShowSearchController(TvShowSearchService tvShowSearchService) {
        this.tvShowSearchService = tvShowSearchService;
    }

    @RequestMapping(value = "/tvshow/search", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<SearchResult<MediaFullDetails>> movieSearch(@RequestParam(value = "q") String searchTerm,
                                                                      @RequestParam(value = "year", required = false) Integer year,
                                                                      @RequestParam(value = "p", required = false) Integer page
    ) throws OMDBException, IOException {

        SearchParamsBuilder searchParamsBuilder = new SearchParamsBuilder();
        searchParamsBuilder.withQuery(searchTerm).withPage(page);

        SearchResult<MediaFullDetails> searchResult = tvShowSearchService.search(searchParamsBuilder.build());
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
