package com.valychbreak.mymedialib.controller.api.favorites;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaShortDetailsAdapter;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.MediaRepository;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class UserAddFavoriteMediaController extends APIController {

    @Autowired
    protected MediaRepository mediaRepository;

    @Autowired
    protected UserMediaRepository userMediaRepository;

    @Autowired
    protected UserMediaCollectionRepository userMediaCollectionRepository;

    @RequestMapping(value = "/user/favourites/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media> addFavoriteToLoggedUser(@RequestBody MediaShortDetailsAdapter mediaDetails) throws Exception {
        User user = getLoggedUser();
        Media media = addMedia(user, mediaDetails);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    private Media addMedia(User user, MediaShortDetails mediaDetails) {
        UserMediaCollection rootUserMediaCollection = user.getRootUserMediaCollection();
        return addMedia(mediaDetails, user, rootUserMediaCollection);
    }

    private Media addMedia(MediaShortDetails mediaDetails, User user, UserMediaCollection userMediaCollection) {
        Media media = mediaRepository.findByImdbId(mediaDetails.getImdbId());

        if(media == null) {
            media = new Media(mediaDetails.getImdbId(), mediaDetails.getTitle());
            mediaRepository.save(media);
        }

        UserMedia userMedia = new UserMedia();
        userMedia.setUser(user);
        userMedia.setMedia(media);
        userMedia.setAddingDate(new Date());

        userMediaRepository.save(userMedia);
        return media;
    }
}
