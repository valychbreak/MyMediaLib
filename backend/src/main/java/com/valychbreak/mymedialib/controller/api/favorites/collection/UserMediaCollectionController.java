package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTO;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOFactory;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserMediaCollectionController extends APIController {
    private UserMediaCollectionRepository userMediaCollectionRepository;

    public UserMediaCollectionController(UserMediaCollectionRepository userMediaCollectionRepository) {
        this.userMediaCollectionRepository = userMediaCollectionRepository;
    }

    @RequestMapping(value = "/collection/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaCollectionDTO> getCollection(@PathVariable String id,
                                                            @RequestAttribute(value = "media", required = false) String includeMedia) throws IOException, OMDBException {
        Long collectionId = Long.parseLong(id);
        UserMediaCollection userMediaCollection = userMediaCollectionRepository.findOne(collectionId);

        MediaCollectionDTO collectionDTO = getMediaCollectionDTO(userMediaCollection, includeMedia);
        return new ResponseEntity<>(collectionDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/collection/root", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaCollectionDTO> getUserRootCollection(@RequestAttribute(value = "media", required = false) String includeMedia) throws IOException, OMDBException {

        User user = getLoggedUser();
        MediaCollectionDTO collectionDTO = getMediaCollectionDTO(user.getRootUserMediaCollection(), includeMedia);
        return new ResponseEntity<>(collectionDTO, HttpStatus.OK);
    }

    private MediaCollectionDTO getMediaCollectionDTO(UserMediaCollection userMediaCollection, String includeMedia) throws IOException, OMDBException {
        MediaCollectionDTOFactory mediaCollectionDTOFactory = new MediaCollectionDTOFactory();

        MediaCollectionDTO collectionDTO;
        if (Boolean.parseBoolean(includeMedia)) {
            collectionDTO = mediaCollectionDTOFactory.createWithMedia(userMediaCollection);
        } else {
            collectionDTO = mediaCollectionDTOFactory.createWithoutMedia(userMediaCollection);
        }
        return collectionDTO;
    }
}
