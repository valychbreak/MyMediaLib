package com.valychbreak.mymedialib.controller.api.favorites;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.services.media.MediaDetailsProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 3/19/17.
 */
@RestController
public class UserFavoriteMediaController extends APIController {

    private UserMediaRepository userMediaRepository;
    private MediaDetailsProvider mediaDetailsProvider;

    public UserFavoriteMediaController(UserMediaRepository userMediaRepository, MediaDetailsProvider mediaDetailsProvider) {
        this.userMediaRepository = userMediaRepository;
        this.mediaDetailsProvider = mediaDetailsProvider;
    }

    @RequestMapping(value = "/user/favourites", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MediaFullDetails>> getFavourites() throws Exception {
        List<MediaFullDetails> mediaList = getUserFavouriteMedia(getLoggedUser());
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{username}/favourites", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MediaFullDetails>> getFavouritesByUsername(@PathVariable String username) throws Exception {
        User user = userRepository.findFirstByUsername(username);
        List<MediaFullDetails> mediaList = getUserFavouriteMedia(user);
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }

    private List<MediaFullDetails> getUserFavouriteMedia(User user) throws IOException {
        List<MediaFullDetails> mediaList = new ArrayList<>();
        for (UserMedia userMedia : userMediaRepository.findByUser(user)) {
            MediaFullDetails details = mediaDetailsProvider.getDetails(userMedia.getMedia());
            details.setFavourite(true);
            mediaList.add(details);
        }
        return mediaList;
    }
}
