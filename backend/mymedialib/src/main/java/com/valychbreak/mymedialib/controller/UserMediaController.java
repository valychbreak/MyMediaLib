package com.valychbreak.mymedialib.controller;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.valychbreak.mymedialib.data.MediaFullDetails;
import com.valychbreak.mymedialib.data.MediaShortDetails;
import com.valychbreak.mymedialib.entity.Media;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserMedia;
import com.valychbreak.mymedialib.repository.MediaRepository;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.tools.adapters.MediaShortDetailsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/user/{userId}/favourites", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MediaFullDetails>> getFavourites(@PathVariable String userId) throws Exception {
        User user = userRepository.findOne(Long.parseLong(userId));
        List<MediaFullDetails> mediaList = new ArrayList<>();//user.getFavourites();
        for (UserMedia userMedia : user.getFavourites()) {
            mediaList.add(userMedia.getMedia().getDetails());
        }
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}/favourites/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media> addFavourite(@PathVariable String userId, @RequestBody MediaShortDetails mediaDetails) throws Exception {
        User user = userRepository.findOne(Long.parseLong(userId));

        Media media = addMedia(mediaDetails, user);

        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}/favourites/addtest", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media> addTestFavourite(@PathVariable String userId) throws Exception {
        User user = userRepository.findOne(Long.parseLong(userId));

        OmdbVideoBasic videoBasic = new OmdbVideoBasic();
        videoBasic.setImdbID("tt0145487");
        videoBasic.setTitle("Spider-Man");
        MediaShortDetails mediaShortDetails = new MediaShortDetailsAdapter(videoBasic);
        Media media = addMedia(mediaShortDetails, user);

        videoBasic.setImdbID("tt1722512");
        videoBasic.setTitle("Ultimate Spider-Man");
        mediaShortDetails = new MediaShortDetailsAdapter(videoBasic);
        media = addMedia(mediaShortDetails, user);

        return new ResponseEntity<>(media, HttpStatus.OK);
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
