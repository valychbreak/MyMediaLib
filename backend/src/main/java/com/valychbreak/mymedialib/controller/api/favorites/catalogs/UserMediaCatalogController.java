package com.valychbreak.mymedialib.controller.api.favorites.catalogs;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.catalog.MediaCatalogDTO;
import com.valychbreak.mymedialib.dto.catalog.MediaCatalogDTOFactory;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import com.valychbreak.mymedialib.repository.UserMediaCatalogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserMediaCatalogController extends APIController {
    private UserMediaCatalogRepository userMediaCatalogRepository;

    public UserMediaCatalogController(UserMediaCatalogRepository userMediaCatalogRepository) {
        this.userMediaCatalogRepository = userMediaCatalogRepository;
    }

    @RequestMapping(value = "/catalog/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaCatalogDTO> getCatalog(@PathVariable String id,
                                                      @RequestAttribute(value = "media", required = false) String includeMedia) throws IOException, OMDBException {
        Long catalogId = Long.parseLong(id);
        UserMediaCatalog userMediaCatalog = userMediaCatalogRepository.findOne(catalogId);

        MediaCatalogDTO catalog = getMediaCatalogDTO(userMediaCatalog, includeMedia);
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/catalog/root", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaCatalogDTO> getUserRootCatalog(@RequestAttribute(value = "media", required = false) String includeMedia) throws IOException, OMDBException {

        User user = getLoggedUser();
        MediaCatalogDTO catalog = getMediaCatalogDTO(user.getRootUserMediaCatalog(), includeMedia);
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private MediaCatalogDTO getMediaCatalogDTO(UserMediaCatalog userMediaCatalog, String includeMedia) throws IOException, OMDBException {
        MediaCatalogDTOFactory mediaCatalogDTOFactory = new MediaCatalogDTOFactory();

        MediaCatalogDTO catalog;
        if (Boolean.parseBoolean(includeMedia)) {
            catalog = mediaCatalogDTOFactory.createWithMedia(userMediaCatalog);
        } else {
            catalog = mediaCatalogDTOFactory.createWithoutMedia(userMediaCatalog);
        }
        return catalog;
    }
}
