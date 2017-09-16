package com.valychbreak.mymedialib.controller.api.user.favorites;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 3/19/17.
 */
@RestController
public class UserFavoriteMediaController extends APIController {

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

    private List<MediaFullDetails> getUserFavouriteMedia(User user) throws OMDBException, IOException {
        List<MediaFullDetails> mediaList = new ArrayList<>();//user.getAllFavorites();
        for (UserMedia userMedia : user.getAllFavorites()) {
            MediaFullDetails details = userMedia.getMedia().getDetails();
            details.setFavourite(true);
            mediaList.add(details);
        }
        return mediaList;
    }
}
