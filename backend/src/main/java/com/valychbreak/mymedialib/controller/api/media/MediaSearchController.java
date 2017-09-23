package com.valychbreak.mymedialib.controller.api.media;

import com.omertron.omdbapi.OMDBException;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.utils.TmdbUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 9/16/17.
 */
@RestController
public class MediaSearchController extends MediaController {
    @RequestMapping(value = "/media/search", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<List<MediaFullDetails>> advancedMediaSearch(@RequestParam(value = "q") String searchTerm,
                                                                      @RequestParam(value = "year", required = false) Integer year,
                                                                      @RequestParam(value = "page", required = false) Integer page
    ) throws OMDBException, IOException {
        Tmdb tmdb = TMDB_INSTANCE;
        MediaResultsPage movieResults = searchMovies(searchTerm, tmdb);
        List<MediaFullDetails> mediaSearchResults = new ArrayList<>();
        for (Media result : movieResults.results) {
            MediaFullDetails media = TmdbUtils.getMediaFullDetailsFromTmdbMedia(tmdb, result);

            if(media != null) {
                mediaSearchResults.add(media);
            }

        }

        return new ResponseEntity<>(mediaSearchResults, HttpStatus.OK);
    }

    private MediaResultsPage searchMovies(String searchTerm, Tmdb tmdb) throws IOException {
        Call<MediaResultsPage> call = tmdb.searchService().multi(searchTerm, null, null, null, null);
        return call.execute().body();
    }

}
