package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.impl.MediaShortDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.MediaRepository;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
public class CollectionAddUserMediaController extends APIController {

    private UserMediaCollectionRepository userMediaCollectionRepository;
    private UserMediaRepository userMediaRepository;
    private MediaRepository mediaRepository;

    public CollectionAddUserMediaController(UserMediaCollectionRepository userMediaCollectionRepository, UserMediaRepository userMediaRepository, MediaRepository mediaRepository) {
        this.userMediaCollectionRepository = userMediaCollectionRepository;
        this.userMediaRepository = userMediaRepository;
        this.mediaRepository = mediaRepository;
    }

    @RequestMapping(value = "/collection/{id}/add-media", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addMediaToCollection(@PathVariable("id") Long collectionId, @RequestBody MediaShortDetailsImpl mediaShortDetails, Principal principal) {
        User loggedUser = getUserFromPrincipal(principal);

        UserMediaCollection userMediaCollection = userMediaCollectionRepository.findOne(collectionId);
        Assert.notNull(userMediaCollection);

        // TODO: Copied from UserFavoriteMediaController. Create service for creating a media + user media
        Media media = getExistingMediaOrCreateNew(mediaShortDetails);
        UserMedia userMedia = getExistingFavoriteMediaOrCreateNew(loggedUser, media);

        userMediaCollection.getUserMediaList().add(userMedia);
        userMediaCollectionRepository.save(userMediaCollection);
        return new ResponseEntity(HttpStatus.OK);
    }

    private Media getExistingMediaOrCreateNew(@RequestBody MediaShortDetailsImpl mediaShortDetails) {
        Media media = mediaRepository.findByImdbId(mediaShortDetails.getImdbId());

        if(media == null) {
            media = new Media(mediaShortDetails.getImdbId(), mediaShortDetails.getTitle());
            mediaRepository.save(media);
        }
        return media;
    }

    private UserMedia getExistingFavoriteMediaOrCreateNew(User loggedUser, Media media) {
        UserMedia userMedia = null;
        List<UserMedia> favorites = userMediaRepository.findByUser(loggedUser);
        for (UserMedia favorite : favorites) {
            if (favorite.getMedia().getImdbId().equals(media.getImdbId())) {
                userMedia = favorite;
            }
        }

        if (userMedia == null) {
            userMedia = new UserMedia();
            userMedia.setUser(loggedUser);
            userMedia.setMedia(media);
            userMedia.setAddingDate(new Date());
            userMediaRepository.save(userMedia);
        }
        return userMedia;
    }
}
