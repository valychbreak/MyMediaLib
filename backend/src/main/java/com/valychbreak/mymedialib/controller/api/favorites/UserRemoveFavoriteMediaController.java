package com.valychbreak.mymedialib.controller.api.favorites;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.impl.MediaShortDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.repository.MediaRepository;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRemoveFavoriteMediaController extends APIController {
    @Autowired
    protected MediaRepository mediaRepository;

    @Autowired
    protected UserMediaRepository userMediaRepository;

    @Autowired
    protected UserMediaCollectionRepository userMediaCollectionRepository;


    @RequestMapping(value = "/user/favourites/remove", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media> removeFavourite(@RequestBody MediaShortDetailsImpl mediaDetails) throws Exception {
        User user = getLoggedUser();

        List<UserMedia> userMediaToRemove = new ArrayList<>();
        for (UserMedia userMedia : new ArrayList<>(user.getRootUserMediaCollection().getUserMediaList())) {
            if(userMedia.getMedia().getImdbId().equals(mediaDetails.getImdbId())) {
                user.getRootUserMediaCollection().getUserMediaList().remove(userMedia);
                userMediaToRemove.add(userMedia);
            }
        }

        userMediaRepository.delete(userMediaToRemove);
        userMediaCollectionRepository.save(user.getRootUserMediaCollection());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
