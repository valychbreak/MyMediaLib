package com.valychbreak.mymedialib.controller.api.media.movie;

import com.valychbreak.mymedialib.controller.api.media.MediaController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.services.media.UserMediaService;
import com.valychbreak.mymedialib.services.media.movie.MovieSearchService;
import com.valychbreak.mymedialib.services.utils.SearchParamsBuilder;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import com.valychbreak.mymedialib.services.utils.SearchResultFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieSearchController extends MediaController {

    private MovieSearchService movieSearchService;
    private UserMediaService userMediaService;

    public MovieSearchController(MovieSearchService movieSearchService, UserMediaService userMediaService) {
        this.movieSearchService = movieSearchService;
        this.userMediaService = userMediaService;
    }

    @RequestMapping(value = "/movie/search", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<SearchResult<MediaFullDetails>> movieSearch(@RequestParam(value = "q") String searchTerm,
                                                                              @RequestParam(value = "year", required = false) Integer year,
                                                                              @RequestParam(value = "p", required = false) Integer page,
                                                                      Principal principal
    ) throws IOException {

        User loggedUser = getUserFromPrincipal(principal);
        SearchParamsBuilder searchParamsBuilder = new SearchParamsBuilder();
        searchParamsBuilder.withQuery(searchTerm).withPage(page);

        SearchResult<MediaFullDetails> searchResult = movieSearchService.search(searchParamsBuilder.build());

        List<MediaFullDetails> processedMediaFullDetails = searchResult.getItems().parallelStream().peek(mediaFullDetails -> {
            boolean isFavorite = userMediaService.isUserFavorite(loggedUser, mediaFullDetails);
            mediaFullDetails.setFavourite(isFavorite);
        }).collect(Collectors.toList());

        SearchResult<MediaFullDetails> mediaFullDetailsSearchResult = new SearchResultFactory().create(searchResult.getPage(), searchResult.getTotalPages(), searchResult.getTotalResults(), processedMediaFullDetails);
        return new ResponseEntity<>(mediaFullDetailsSearchResult, HttpStatus.OK);
    }
}
