package com.valychbreak.mymedialib.controller.api.media;

import com.valychbreak.mymedialib.data.MediaSearchType;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.exception.ExternalAPIException;
import com.valychbreak.mymedialib.exception.MyMediaLibException;
import com.valychbreak.mymedialib.services.media.MediaSearchService;
import com.valychbreak.mymedialib.services.media.UserMediaService;
import com.valychbreak.mymedialib.services.media.movie.MovieSearchService;
import com.valychbreak.mymedialib.services.media.tvshow.TvShowSearchService;
import com.valychbreak.mymedialib.services.utils.SearchParams;
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

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
    ) throws ExternalAPIException, MyMediaLibException {

        User loggedUser = getUserFromPrincipal(principal);

        SearchParams searchParams = new SearchParamsBuilder()
                .withQuery(searchTerm)
                .withPage(page)
                .build();

        SearchResult<MediaFullDetails> searchResult = performSearch(mediaType, searchParams);
        List<MediaFullDetails> processedMediaFullDetails = getEnrichedMediaFullDetails(searchResult, loggedUser);

        SearchResult<MediaFullDetails> mediaFullDetailsSearchResult = new SearchResultFactory().create(searchResult.getPage(), searchResult.getTotalPages(), searchResult.getTotalResults(), processedMediaFullDetails);
        return new ResponseEntity<>(mediaFullDetailsSearchResult, HttpStatus.OK);
    }

    private List<MediaFullDetails> getEnrichedMediaFullDetails(SearchResult<MediaFullDetails> searchResult, User loggedUser) {
        return searchResult.getItems().parallelStream().peek(mediaFullDetails -> {
                boolean isFavorite = userMediaService.isUserFavorite(loggedUser, mediaFullDetails);
                mediaFullDetails.setFavourite(isFavorite);
            }).collect(Collectors.toList());
    }

    private SearchResult<MediaFullDetails> performSearch(String mediaType, SearchParams searchParams) throws MyMediaLibException, ExternalAPIException {
        SearchResult<MediaFullDetails> searchResult;
        MediaSearchType mediaSearchType = MediaSearchType.get(mediaType);

        switch (mediaSearchType) {
            case MEDIA:
                searchResult = mediaSearchService.search(searchParams);
                break;

            case MOVIE:
                searchResult = movieSearchService.search(searchParams);
                break;

            case TVSHOW:
                searchResult = tvShowSearchService.search(searchParams);
                break;

            default:
                throw new MyMediaLibException("Media-type parameter " + mediaType + " was not mapped to any existing type");
        }

        return searchResult;
    }
}
