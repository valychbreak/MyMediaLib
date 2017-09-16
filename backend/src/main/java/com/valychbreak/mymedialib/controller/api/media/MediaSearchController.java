package com.valychbreak.mymedialib.controller.api.media;

import com.omertron.omdbapi.OMDBException;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsTmdbMovieAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 9/16/17.
 */
@Controller
public class MediaSearchController extends MediaController {
    @RequestMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<List<MediaFullDetails>> advancedMediaSearch(@RequestParam(value = "q") String searchTerm,
                                                                      @RequestParam(value = "year", required = false) Integer year,
                                                                      @RequestParam(value = "page", required = false) Integer page
    ) throws OMDBException, IOException {
        Tmdb tmdb = TMDB_INSTANCE;
        MediaResultsPage movieResults = searchMovies(searchTerm, tmdb);
        List<MediaFullDetails> mediaSearchResults = new ArrayList<>();
        for (Media result : movieResults.results) {
            MediaFullDetails media = getMediaFullDetailsFromTmdbMedia(tmdb, result);

            if(media != null) {
                mediaSearchResults.add(media);
            }

        }

        return new ResponseEntity<>(mediaSearchResults, HttpStatus.OK);
    }

    private MediaFullDetails getMediaFullDetailsFromTmdbMedia(Tmdb tmdb, Media result) throws IOException {
        MediaFullDetails media = null;

        switch (result.media_type) {
            case TV:
                TvShow tvShow = requestDetailedTmdbTvShow(tmdb, result.tvShow);
                media = new MediaFullDetailsTmdbMovieAdapter(tvShow);
                break;
            case MOVIE:
                Movie movie = requestDetailedTmdbTvShow(tmdb, result.movie);
                //FIXME: get rid of imdbId
                if(movie != null && StringUtils.isNotBlank(movie.imdb_id) && movie.backdrop_path != null) {
                    media = new MediaFullDetailsTmdbMovieAdapter(movie);
                }
                break;
        }

        return media;
    }

    private MediaResultsPage searchMovies(String searchTerm, Tmdb tmdb) throws IOException {
        Call<MediaResultsPage> call = tmdb.searchService().multi(searchTerm, null, null, null, null);
        return call.execute().body();
    }

    protected Movie requestDetailedTmdbTvShow(Tmdb tmdb, BaseMovie result) throws IOException {

        Call<Movie> summary = tmdb.moviesService().summary(result.id, null, new AppendToResponse(AppendToResponseItem.EXTERNAL_IDS));
        return summary.execute().body();
    }

    protected TvShow requestDetailedTmdbTvShow(Tmdb tmdb, BaseTvShow result) throws IOException {

        Call<TvShow> summary = tmdb.tvService().tv(result.id, null, new AppendToResponse(AppendToResponseItem.EXTERNAL_IDS));
        return summary.execute().body();
    }
}
