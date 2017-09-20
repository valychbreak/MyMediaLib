package com.valychbreak.mymedialib.controller.api.media;

import com.omertron.omdbapi.OMDBException;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsTmdbMovieAdapter;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.services.TmdbMediaProvider;
import com.valychbreak.mymedialib.utils.TmdbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by valych on 9/16/17.
 */
@RestController
public class MediaDetailsController extends MediaController {
    private UserMediaRepository userMediaRepository;

    @Autowired
    public MediaDetailsController(UserMediaRepository userMediaRepository) {
        this.userMediaRepository = userMediaRepository;
    }

    @RequestMapping(value = "/details/{imdbId}", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<MediaFullDetailsImpl> getMediaDetailsByImdbId(@PathVariable String imdbId) throws OMDBException, IOException {
        /*OmdbVideoFull omdbVideo = new OmdbVideoProvider().getOmdbVideo(imdbId);
        MediaFullDetailsImpl media = new MediaFullDetailsAdapter(omdbVideo);

        User user = getLoggedUser();
        boolean isFavourite = isUserFavourite(user, media);

        media.setFavourite(isFavourite);
        return new ResponseEntity<>(media, HttpStatus.OK);*/

        com.uwetrottmann.tmdb2.entities.Media media = new TmdbMediaProvider().getMediaBy(imdbId);
        MediaFullDetailsImpl mediaFullDetails = (MediaFullDetailsImpl) TmdbUtils.getMediaFullDetailsFromTmdbMedia(TMDB_INSTANCE, media);
        return new ResponseEntity<>(mediaFullDetails, HttpStatus.OK);

    }

    private boolean isUserFavourite(User user, MediaShortDetails media) throws OMDBException {
        boolean isFavourite = false;
        List<UserMedia> userMediaList = userMediaRepository.findByUser(user);
        for (UserMedia userMedia : userMediaList) {
            if(userMedia.getMedia().getImdbId().equals(media.getImdbId())) {
                isFavourite = true;
            }
        }
        return isFavourite;
    }
}
