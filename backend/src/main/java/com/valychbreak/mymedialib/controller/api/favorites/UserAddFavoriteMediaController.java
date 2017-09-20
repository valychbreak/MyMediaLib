package com.valychbreak.mymedialib.controller.api.favorites;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaShortDetailsAdapter;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import com.valychbreak.mymedialib.repository.MediaRepository;
import com.valychbreak.mymedialib.repository.UserMediaCatalogRepository;
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
    protected UserMediaCatalogRepository userMediaCatalogRepository;

    @RequestMapping(value = "/user/favourites/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media> addFavoriteToLoggedUser(@RequestBody MediaShortDetailsAdapter mediaDetails) throws Exception {
        User user = getLoggedUser();
        Media media = addMedia(user, mediaDetails);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/favourites/{catalogId}/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media> addFavourite(@PathVariable String catalogId, @RequestBody MediaShortDetailsAdapter mediaDetails) throws Exception {
        User user = getLoggedUser();
        UserMediaCatalog userMediaCatalog = userMediaCatalogRepository.findOne(Long.parseLong(catalogId));
        Media media = addMedia(mediaDetails, user, userMediaCatalog);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    private Media addMedia(User user, MediaShortDetails mediaDetails) {
        UserMediaCatalog rootUserMediaCatalog = user.getRootUserMediaCatalog();
        return addMedia(mediaDetails, user, rootUserMediaCatalog);
    }

    private Media addMedia(MediaShortDetails mediaDetails, User user, UserMediaCatalog userMediaCatalog) {
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


        userMediaCatalog.getUserMediaList().add(userMedia);
        userMediaCatalogRepository.save(userMediaCatalog);
        return media;
    }
}
