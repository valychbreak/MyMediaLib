package com.valychbreak.mymedialib.controller.api.user.favorites;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import com.valychbreak.mymedialib.repository.MediaRepository;
import com.valychbreak.mymedialib.repository.UserMediaCatalogRepository;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
import com.valychbreak.mymedialib.data.movie.adapters.MediaShortDetailsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by valych on 3/19/17.
 */
@RestController
public class UserFavoriteMediaController extends APIController {

    @Autowired
    protected UserMediaRepository userMediaRepository;


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



    @RequestMapping(value = "/user/{username}/favourites/remove", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media> removeFavourite(@PathVariable String username, @RequestBody MediaShortDetailsAdapter mediaDetails) throws Exception {
        User user = userRepository.findFirstByUsername(username);

        OmdbVideoProvider videoProvider = new OmdbVideoProvider();
        //MediaShortDetails mediaShortDetails = new MediaShortDetailsAdapter(mediaDetails);
        //Media media = addMedia(mediaDetails, user);

        List<UserMedia> mediaToRemove = new ArrayList<>();
        for (UserMedia userMedia : user.getRootUserMediaCatalog().getUserMediaList()) {
            if(userMedia.getMedia().getImdbId().equals(mediaDetails.getImdbId())) {

            }
        }
        for (UserMedia userMedia : user.getAllFavorites()) {
            if(userMedia.getMedia().getImdbId().equals(mediaDetails.getImdbId())) {
                userMediaRepository.delete(userMedia);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
