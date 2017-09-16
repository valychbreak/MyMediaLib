package com.valychbreak.mymedialib.controller.api.media;

import com.omertron.omdbapi.OMDBException;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsTmdbMovieAdapter;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.services.TmdbMovieProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by valych on 9/16/17.
 */
public class MediaDetailsController extends MediaController {
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
}
