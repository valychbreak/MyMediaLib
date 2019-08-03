package com.valychbreak.mymedialib.controller.api.media;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.services.TmdbMediaProvider;
import com.valychbreak.mymedialib.utils.TmdbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Created by valych on 9/16/17.
 */
@RestController
public class MediaDetailsController extends MediaController {
    private UserMediaRepository userMediaRepository;
    private TmdbMediaProvider tmdbMediaProvider;

    public MediaDetailsController(UserMediaRepository userMediaRepository, TmdbMediaProvider tmdbMediaProvider) {
        this.userMediaRepository = userMediaRepository;
        this.tmdbMediaProvider = tmdbMediaProvider;
    }

    @RequestMapping(value = "/media/details/{imdbId}", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<MediaFullDetailsImpl> getMediaDetailsByImdbId(@PathVariable String imdbId, Principal principal) throws OMDBException, IOException {
        Assert.notNull(principal, "Principal must not be null");

        User user = getUserFromPrincipal(principal);
        com.uwetrottmann.tmdb2.entities.Media media = tmdbMediaProvider.getMediaBy(imdbId);
        MediaFullDetailsImpl mediaFullDetails = (MediaFullDetailsImpl) TmdbUtils.getMediaFullDetailsFromTmdbMedia(tmdb, media);
        boolean userFavourite = isUserFavourite(user, mediaFullDetails);
        mediaFullDetails.setFavourite(userFavourite);
        return new ResponseEntity<>(mediaFullDetails, HttpStatus.OK);

    }

    private boolean isUserFavourite(User user, MediaShortDetails media) {
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
