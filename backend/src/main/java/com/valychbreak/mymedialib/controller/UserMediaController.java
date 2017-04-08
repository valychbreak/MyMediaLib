package com.valychbreak.mymedialib.controller;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.valychbreak.mymedialib.data.MediaFullDetails;
import com.valychbreak.mymedialib.data.MediaShortDetails;
import com.valychbreak.mymedialib.entity.Media;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserMedia;
import com.valychbreak.mymedialib.repository.MediaRepository;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
import com.valychbreak.mymedialib.tools.adapters.MediaShortDetailsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by valych on 3/19/17.
 */
@RestController
public class UserMediaController {
    private UserRepository userRepository;
    private MediaRepository mediaRepository;
    private UserMediaRepository userMediaRepository;


    @Autowired
    public UserMediaController(UserRepository userRepository, MediaRepository mediaRepository, UserMediaRepository userMediaRepository) {
        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
        this.userMediaRepository = userMediaRepository;
    }

    @RequestMapping(value = "/user/favourites", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MediaFullDetails>> getFavourites() throws Exception {
        String username = ((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = getUserByUsername(username);
        List<MediaFullDetails> mediaList = getUserFavouriteMedia(user);
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{username}/favourites", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MediaFullDetails>> getFavouritesByUsername(@PathVariable String username) throws Exception {
        User user = getUserByUsername(username);
        List<MediaFullDetails> mediaList = getUserFavouriteMedia(user);
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }

    private User getUserByUsername(@PathVariable String username) {
        List<User> userList = userRepository.findByUsername(username);
        return userList.size() > 0 ? userList.get(0) : null;
    }

    private List<MediaFullDetails> getUserFavouriteMedia(User user) throws OMDBException {
        List<MediaFullDetails> mediaList = new ArrayList<>();//user.getFavourites();
        for (UserMedia userMedia : user.getFavourites()) {
            MediaFullDetails details = userMedia.getMedia().getDetails();
            details.setFavourite(true);
            mediaList.add(details);
        }
        return mediaList;
    }

    @RequestMapping(value = "/user/{username}/favourites/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media> addFavourite(@PathVariable String username, @RequestBody MediaShortDetailsAdapter mediaDetails) throws Exception {
        User user = getUserByUsername(username);
        Media media = addMedia(mediaDetails, user);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{username}/favourites/remove", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media> removeFavourite(@PathVariable String username, @RequestBody MediaShortDetailsAdapter mediaDetails) throws Exception {
        User user = getUserByUsername(username);

        OmdbVideoProvider videoProvider = new OmdbVideoProvider();
        //MediaShortDetails mediaShortDetails = new MediaShortDetailsAdapter(mediaDetails);
        //Media media = addMedia(mediaDetails, user);

        for (UserMedia userMedia : user.getFavourites()) {
            if(userMedia.getMedia().getImdbId().equals(mediaDetails.getImdbId())) {
                userMediaRepository.delete(userMedia);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Media addMedia(@RequestBody MediaShortDetails mediaDetails, User user) {
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
