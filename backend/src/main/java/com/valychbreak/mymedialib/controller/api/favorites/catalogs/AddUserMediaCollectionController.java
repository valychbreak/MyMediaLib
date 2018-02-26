package com.valychbreak.mymedialib.controller.api.favorites.catalogs;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.catalog.MediaCollectionDTO;
import com.valychbreak.mymedialib.dto.catalog.MediaCollectionDTOFactory;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.exception.CollectionNotFoundException;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddUserMediaCollectionController extends APIController {

    private UserMediaCollectionRepository userMediaCollectionRepository;

    @Autowired
    public AddUserMediaCollectionController(UserMediaCollectionRepository userMediaCollectionRepository) {
        this.userMediaCollectionRepository = userMediaCollectionRepository;
    }

    @RequestMapping(value = "/catalog/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaCollectionDTO> addMediaCollection(@RequestBody MediaCollectionDTO newMediaCollection) throws Exception {
        Long parentCollectionId = getParentId(newMediaCollection);
        UserMediaCollection parentCollection = userMediaCollectionRepository.findOne(parentCollectionId);

        if (parentCollection == null) {
            throw new CollectionNotFoundException("Collection with " + parentCollectionId + " id does not exist. Cannot add new catalog " + newMediaCollection.getName());
        }

        UserMediaCollection newUserMediaCollection = new UserMediaCollection(newMediaCollection.getName());
        newUserMediaCollection.setParentUserMediaCollection(parentCollection);

        userMediaCollectionRepository.save(newUserMediaCollection);

        MediaCollectionDTO mediaCollectionDTO = new MediaCollectionDTOFactory().createWithMedia(newUserMediaCollection);
        return new ResponseEntity<>(mediaCollectionDTO, HttpStatus.OK);
    }

    private Long getParentId(MediaCollectionDTO newMediaCollection) {
        MediaCollectionDTO parent = newMediaCollection.getParent();
        return parent == null ? null : parent.getId();
    }
}
