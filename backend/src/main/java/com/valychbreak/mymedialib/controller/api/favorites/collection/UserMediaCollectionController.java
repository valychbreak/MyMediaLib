package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTO;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOFactory;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserMediaCollectionController extends APIController {
    private static final Logger LOG = LoggerFactory.getLogger(UserMediaCollectionController.class);

    private UserMediaCollectionRepository userMediaCollectionRepository;
    private MediaCollectionDTOFactory mediaCollectionDTOFactory;

    public UserMediaCollectionController(UserMediaCollectionRepository userMediaCollectionRepository, MediaCollectionDTOFactory mediaCollectionDTOFactory) {
        this.userMediaCollectionRepository = userMediaCollectionRepository;
        this.mediaCollectionDTOFactory = mediaCollectionDTOFactory;
    }

    @RequestMapping(value = "/collection/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaCollectionDTO> getCollection(@PathVariable String id,
                                                            @RequestParam(value = "media", required = false) String includeMedia, Principal principal) throws IOException {
        User loggedUser = getUserFromPrincipal(principal);

        Long collectionId = Long.parseLong(id);
        UserMediaCollection userMediaCollection = userMediaCollectionRepository.findOne(collectionId);

        MediaCollectionDTO collectionDTO = getMediaCollectionDTO(userMediaCollection, includeMedia, loggedUser);
        return new ResponseEntity<>(collectionDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/collection/root", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaCollectionDTO> getUserRootCollection(@RequestParam(value = "media", required = false) String includeMedia) throws IOException {

        User user = getLoggedUser();
        MediaCollectionDTO collectionDTO = getMediaCollectionDTO(user.getRootUserMediaCollection(), includeMedia, user);
        return new ResponseEntity<>(collectionDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/collection/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MediaCollectionDTO>> getAllUserCollections(@RequestParam(value = "media", required = false) String includeMedia, Principal principal) {

        User user = userRepository.findFirstByUsername(principal.getName());
        Collection<UserMediaCollection> userMediaCollections = userMediaCollectionRepository.findAllByOwner(user);

        List<MediaCollectionDTO> collectionDTOList = convertToDTO(includeMedia, user, userMediaCollections);
        return new ResponseEntity<>(collectionDTOList, HttpStatus.OK);
    }

    private List<MediaCollectionDTO> convertToDTO(@RequestParam(value = "media", required = false) String includeMedia, User user, Collection<UserMediaCollection> userMediaCollections) {
        return userMediaCollections.parallelStream()
                    .map(userMediaCollection -> {
                        try {
                            return Optional.ofNullable(this.getMediaCollectionDTO(userMediaCollection, includeMedia, user));
                        } catch (IOException e) {
                            LOG.error("Media Collection was not converted to DTO", e);
                        }

                        return Optional.<MediaCollectionDTO>empty();
                    })
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
    }

    private MediaCollectionDTO getMediaCollectionDTO(UserMediaCollection userMediaCollection, String includeMedia, User user) throws IOException {
        MediaCollectionDTO collectionDTO;
        if (Boolean.parseBoolean(includeMedia)) {
            collectionDTO = mediaCollectionDTOFactory.createWithMedia(userMediaCollection, user);
        } else {
            collectionDTO = mediaCollectionDTOFactory.createWithoutMedia(userMediaCollection, user);
        }
        return collectionDTO;
    }
}
