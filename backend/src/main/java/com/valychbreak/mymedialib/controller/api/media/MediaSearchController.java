package com.valychbreak.mymedialib.controller.api.media;

import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.exception.MyMediaLibException;
import com.valychbreak.mymedialib.services.media.MediaSearchService;
import com.valychbreak.mymedialib.services.media.UserMediaService;
import com.valychbreak.mymedialib.services.media.movie.MovieSearchService;
import com.valychbreak.mymedialib.services.media.tvshow.TvShowSearchService;
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

/**
 * Created by valych on 9/16/17.
 */
@RestController
public class MediaSearchController extends MediaController {

    private MediaSearchService mediaSearchService;
    private MovieSearchService movieSearchService;
    private TvShowSearchService tvShowSearchService;
    private UserMediaService userMediaService;


    public MediaSearchController(MediaSearchService mediaSearchService, MovieSearchService movieSearchService, TvShowSearchService tvShowSearchService, UserMediaService userMediaService) {
        this.mediaSearchService = mediaSearchService;
        this.movieSearchService = movieSearchService;
        this.tvShowSearchService = tvShowSearchService;
        this.userMediaService = userMediaService;
    }

    @RequestMapping(value = "/media/search", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<SearchResult<MediaFullDetails>> advancedMediaSearch(@RequestParam(value = "q") String searchTerm,
                                                                              @RequestParam(value = "media-type") String mediaType,
                                                                              @RequestParam(value = "year", required = false) Integer year,
                                                                              @RequestParam(value = "p", required = false) Integer page,
                                                                              Principal principal
    ) throws IOException, MyMediaLibException {

        User loggedUser = getUserFromPrincipal(principal);
        SearchParamsBuilder searchParamsBuilder = new SearchParamsBuilder();
        searchParamsBuilder.withQuery(searchTerm).withPage(page);

        SearchResult<MediaFullDetails> searchResult = null;
        if (mediaType.equalsIgnoreCase("MEDIA")) {
            searchResult = mediaSearchService.search(searchParamsBuilder.build());
        } else if (mediaType.equalsIgnoreCase("MOVIE")) {
            searchResult = movieSearchService.search(searchParamsBuilder.build());
        } else if (mediaType.equalsIgnoreCase("TVSHOW")) {
            searchResult = tvShowSearchService.search(searchParamsBuilder.build());
        } else {
            throw new MyMediaLibException("Media-type parameter " + mediaType + " was not mapped to any existing type");
        }

        List<MediaFullDetails> processedMediaFullDetails = searchResult.getItems().parallelStream().peek(mediaFullDetails -> {
            boolean isFavorite = userMediaService.isUserFavorite(loggedUser, mediaFullDetails);
            mediaFullDetails.setFavourite(isFavorite);
        }).collect(Collectors.toList());

        SearchResult<MediaFullDetails> mediaFullDetailsSearchResult = new SearchResultFactory().create(searchResult.getPage(), searchResult.getTotalPages(), searchResult.getTotalResults(), processedMediaFullDetails);
        return new ResponseEntity<>(mediaFullDetailsSearchResult, HttpStatus.OK);
    }
}
