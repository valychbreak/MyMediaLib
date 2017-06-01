package com.valychbreak.mymedialib.controller;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.model.SearchResults;
import com.omertron.omdbapi.tools.OmdbBuilder;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.AppendToResponse;
import com.uwetrottmann.tmdb2.entities.FindResults;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.uwetrottmann.tmdb2.enumerations.ExternalSource;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsTmdbMovieAdapter;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsAdapter;
import com.valychbreak.mymedialib.services.TmdbMovieProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valeriy on 3/18/2017.
 */
@RestController
@RequestMapping(value = "/api/movie")
public class MediaController {
    public static final Logger logger = LoggerFactory.getLogger(MediaController.class);
    public static final Tmdb TMDB_INSTANCE = new Tmdb("01e924145da414b33cdc651619bb694b");

    private UserRepository userRepository;

    @Autowired
    public MediaController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/imdb/get/{imdbId}", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<MediaFullDetailsImpl> getMovieByImdbId(@PathVariable String imdbId) throws OMDBException, IOException {
        /*OmdbVideoFull omdbVideo = new OmdbVideoProvider().getOmdbVideo(imdbId);
        MediaFullDetailsImpl media = new MediaFullDetailsAdapter(omdbVideo);

        User user = getLoggedUser();
        boolean isFavourite = isUserFavourite(user, media);

        media.setFavourite(isFavourite);
        return new ResponseEntity<>(media, HttpStatus.OK);*/

        Movie movie = new TmdbMovieProvider().getMovieBy(imdbId);
        MediaFullDetailsImpl media = new MediaFullDetailsTmdbMovieAdapter(movie);
        return new ResponseEntity<>(media, HttpStatus.OK);

    }

    private boolean isUserFavourite(User user, MediaShortDetails media) throws OMDBException {
        boolean isFavourite = false;
        List<UserMedia> userMediaList = user.getAllFavorites();
        for (UserMedia userMedia : userMediaList) {
            if(userMedia.getMedia().getImdbId().equals(media.getImdbId())) {
                isFavourite = true;
            }
        }
        return isFavourite;
    }

    @RequestMapping(value = "/advanced-search", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<List<MediaFullDetails>> advanceSearchMovies(@RequestParam(value = "s") String searchTerm,
                                                                      @RequestParam(value = "year", required = false) Integer year,
                                                                      @RequestParam(value = "page", required = false) Integer page
    ) throws OMDBException, IOException {
        /*OmdbApi api = new OmdbApi();
        OmdbBuilder omdbBuilder = new OmdbBuilder();

        User user = getLoggedUser();

        omdbBuilder.setSearchTerm(searchTerm);

        if(year != null) {
            omdbBuilder.setYear(year);
        }

        SearchResults results = api.search(omdbBuilder.build());

        List<MediaFullDetails> mediaSearchResult = new ArrayList<>();
        for (OmdbVideoBasic videoBasic : results.getResults()) {
            OmdbVideoFull omdbVideo = new OmdbVideoProvider().getOmdbVideo(videoBasic.getImdbID());
            MediaFullDetails fullDetailsAdapter = new MediaFullDetailsAdapter(omdbVideo);
            *//*boolean isFavourite = isUserFavourite(user, fullDetailsAdapter);
            fullDetailsAdapter.setFavourite(isFavourite);*//*
            mediaSearchResult.add(fullDetailsAdapter);
        }

        //logger.info("is response: " + results.isResponse() + "; current results: " + results.getResults().size() + "; total results = " + results.getTotalResults());
        return new ResponseEntity<>(mediaSearchResult, HttpStatus.OK);*/

        Tmdb tmdb = TMDB_INSTANCE;
        Call<MovieResultsPage> call = tmdb.searchService().movie(searchTerm, null, null, null, null,
                null, null);
        MovieResultsPage movieResults = call.execute().body();
        List<MediaFullDetails> mediaSearchResults = new ArrayList<>();
        for (Movie result : movieResults.results) {
            Call<Movie> summary = tmdb.moviesService().summary(result.id, null, new AppendToResponse(AppendToResponseItem.EXTERNAL_IDS));
            Movie movie = summary.execute().body();

            //FIXME: get rid of imdbId
            if(movie == null || StringUtils.isBlank(movie.imdb_id)) {
                continue;
            }
            MediaFullDetails media = new MediaFullDetailsTmdbMovieAdapter(movie);
            mediaSearchResults.add(media);
        }

        return new ResponseEntity<List<MediaFullDetails>>(mediaSearchResults, HttpStatus.OK);
    }

    private User getLoggedUser() {
        return userRepository.findFirstByUsername("test");
    }
}
