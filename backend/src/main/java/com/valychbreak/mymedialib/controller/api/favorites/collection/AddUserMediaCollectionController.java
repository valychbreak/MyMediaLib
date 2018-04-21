package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTO;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOFactory;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AddUserMediaCollectionController extends APIController {

    private UserMediaCollectionRepository userMediaCollectionRepository;
    private MediaCollectionDTOFactory mediaCollectionDTOFactory;

    @Autowired
    public AddUserMediaCollectionController(UserMediaCollectionRepository userMediaCollectionRepository, MediaCollectionDTOFactory mediaCollectionDTOFactory) {
        this.userMediaCollectionRepository = userMediaCollectionRepository;
        this.mediaCollectionDTOFactory = mediaCollectionDTOFactory;
    }

    @RequestMapping(value = "/collection/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaCollectionDTO> addMediaCollection(@RequestBody MediaCollectionDTO newMediaCollection, Principal principal) throws Exception {
        Assert.notNull(principal, "Principal cannot be null");

        User user = userRepository.findFirstByUsername(principal.getName());

        Assert.notNull(user, "User cannot be null");

        UserMediaCollection newUserMediaCollection = new UserMediaCollection(newMediaCollection.getName(), user);
        userMediaCollectionRepository.save(newUserMediaCollection);

        MediaCollectionDTO mediaCollectionDTO = mediaCollectionDTOFactory.createWithMedia(newUserMediaCollection, user);
        return new ResponseEntity<>(mediaCollectionDTO, HttpStatus.OK);
    }
}
