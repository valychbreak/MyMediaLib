package com.valychbreak.mymedialib.controller.api.media;

import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.services.TmdbMediaProvider;
import com.valychbreak.mymedialib.services.utils.TmdbService;
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
    private TmdbService tmdbService;

    public MediaDetailsController(UserMediaRepository userMediaRepository, TmdbMediaProvider tmdbMediaProvider, TmdbService tmdbService) {
        this.userMediaRepository = userMediaRepository;
        this.tmdbMediaProvider = tmdbMediaProvider;
        this.tmdbService = tmdbService;
    }

    @RequestMapping(value = "/media/details/{imdbId}", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<MediaFullDetailsImpl> getMediaDetailsByImdbId(@PathVariable String imdbId, Principal principal) throws IOException {
        Assert.notNull(principal, "Principal must not be null");

        com.uwetrottmann.tmdb2.entities.Media media = tmdbMediaProvider.getMediaBy(imdbId);
        MediaFullDetailsImpl mediaFullDetails = (MediaFullDetailsImpl) tmdbService.getMediaDetails(media)
                .orElseThrow(() -> new IllegalArgumentException("Media details were not found for imdbId: " + imdbId));

        User user = getUserFromPrincipal(principal);
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
