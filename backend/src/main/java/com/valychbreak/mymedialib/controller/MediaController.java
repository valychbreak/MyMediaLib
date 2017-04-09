package com.valychbreak.mymedialib.controller;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.model.SearchResults;
import com.omertron.omdbapi.tools.OmdbBuilder;
import com.valychbreak.mymedialib.data.MediaFullDetails;
import com.valychbreak.mymedialib.data.MediaShortDetails;
import com.valychbreak.mymedialib.entity.Media;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserMedia;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
import com.valychbreak.mymedialib.tools.adapters.MediaFullDetailsAdapter;
import com.valychbreak.mymedialib.tools.adapters.MediaShortDetailsAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valeriy on 3/18/2017.
 */
@RestController
@RequestMapping(value = "/api/movie")
public class MediaController {
    public static final Logger logger = LoggerFactory.getLogger(MediaController.class);

    private UserRepository userRepository;

    @Autowired
    public MediaController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/imdb/get/{imdbId}", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<MediaFullDetailsAdapter> getMovieByImdbId(@PathVariable String imdbId) throws OMDBException {
        OmdbVideoFull omdbVideo = new OmdbVideoProvider().getOmdbVideo(imdbId);
        MediaFullDetailsAdapter media = new MediaFullDetailsAdapter(omdbVideo);

        User user = getLoggedUser();
        boolean isFavourite = isUserFavourite(user, media);

        media.setFavourite(isFavourite);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    private boolean isUserFavourite(User user, MediaShortDetails media) throws OMDBException {
        boolean isFavourite = false;
        List<UserMedia> userMediaList = user.getFavourites();
        for (UserMedia userMedia : userMediaList) {
            if(userMedia.getMedia().getShortDetails().getImdbId().equals(media.getImdbId())) {
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
    ) throws OMDBException {
        OmdbApi api = new OmdbApi();
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
            boolean isFavourite = isUserFavourite(user, fullDetailsAdapter);
            fullDetailsAdapter.setFavourite(isFavourite);
            mediaSearchResult.add(fullDetailsAdapter);
        }

        //logger.info("is response: " + results.isResponse() + "; current results: " + results.getResults().size() + "; total results = " + results.getTotalResults());
        return new ResponseEntity<>(mediaSearchResult, HttpStatus.OK);
    }

    private User getLoggedUser() {
        return userRepository.findFirstByUsername("test");
    }
}
