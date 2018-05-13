package com.valychbreak.mymedialib.controller.api.media;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.services.media.MediaSearchService;
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
import java.security.Principal;

/**
 * Created by valych on 9/16/17.
 */
@RestController
public class MediaSearchController extends MediaController {

    private MediaSearchService mediaSearchService;

    @Autowired
    public MediaSearchController(MediaSearchService mediaSearchService) {
        this.mediaSearchService = mediaSearchService;
    }

    @RequestMapping(value = "/media/search", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<SearchResult<MediaFullDetails>> advancedMediaSearch(@RequestParam(value = "q") String searchTerm,
                                                                      @RequestParam(value = "year", required = false) Integer year,
                                                                      @RequestParam(value = "p", required = false) Integer page,
                                                                              Principal principal
    ) throws IOException {

        User loggedUser = getUserFromPrincipal(principal);
        SearchParamsBuilder searchParamsBuilder = new SearchParamsBuilder();
        searchParamsBuilder.withQuery(searchTerm).withPage(page);

        SearchResult<MediaFullDetails> searchResult = mediaSearchService.search(searchParamsBuilder.build(), loggedUser);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
