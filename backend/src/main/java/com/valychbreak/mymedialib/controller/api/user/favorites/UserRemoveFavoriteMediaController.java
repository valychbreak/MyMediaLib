package com.valychbreak.mymedialib.controller.api.user.favorites;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaShortDetailsAdapter;
import com.valychbreak.mymedialib.data.movie.impl.MediaShortDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.repository.MediaRepository;
import com.valychbreak.mymedialib.repository.UserMediaCatalogRepository;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
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
    protected UserMediaCatalogRepository userMediaCatalogRepository;


    @RequestMapping(value = "/user/favourites/remove", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Media> removeFavourite(@RequestBody MediaShortDetailsImpl mediaDetails) throws Exception {
        User user = getLoggedUser();

        OmdbVideoProvider videoProvider = new OmdbVideoProvider();
        //MediaShortDetails mediaShortDetails = new MediaShortDetailsAdapter(mediaDetails);
        //Media media = addMedia(mediaDetails, user);

        List<UserMedia> mediaToRemove = new ArrayList<>();
        for (UserMedia userMedia : new ArrayList<>(user.getRootUserMediaCatalog().getUserMediaList())) {
            if(userMedia.getMedia().getImdbId().equals(mediaDetails.getImdbId())) {
                user.getRootUserMediaCatalog().getUserMediaList().remove(userMedia);
            }
        }

        userMediaCatalogRepository.save(user.getRootUserMediaCatalog());

        for (UserMedia userMedia : user.getAllFavorites()) {
            if(userMedia.getMedia().getImdbId().equals(mediaDetails.getImdbId())) {
                userMediaRepository.delete(userMedia);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
