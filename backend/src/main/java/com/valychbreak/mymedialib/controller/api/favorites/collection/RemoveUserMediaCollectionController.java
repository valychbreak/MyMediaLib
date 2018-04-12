package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.exception.CollectionNotFoundException;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoveUserMediaCollectionController extends APIController {

    private UserMediaCollectionRepository userMediaCollectionRepository;

    @Autowired
    public RemoveUserMediaCollectionController(UserMediaCollectionRepository userMediaCollectionRepository) {
        this.userMediaCollectionRepository = userMediaCollectionRepository;
    }

    @RequestMapping(value = "/collection/{id}/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeCollection(@PathVariable("id") Long collectionId) throws CollectionNotFoundException {
        Assert.notNull(collectionId);

        UserMediaCollection userMediaCollection = userMediaCollectionRepository.findOne(collectionId);
        if (userMediaCollection == null) {
            throw new CollectionNotFoundException();
        }

        userMediaCollectionRepository.delete(userMediaCollection);
        return new ResponseEntity(HttpStatus.OK);
    }
}
