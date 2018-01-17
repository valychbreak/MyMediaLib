package com.valychbreak.mymedialib.controller.api.favorites.catalogs;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.catalog.MediaCatalogDTO;
import com.valychbreak.mymedialib.dto.catalog.MediaCatalogDTOFactory;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import com.valychbreak.mymedialib.exception.CatalogNotFoundException;
import com.valychbreak.mymedialib.repository.UserMediaCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddUserMediaCatalogController extends APIController {

    private UserMediaCatalogRepository userMediaCatalogRepository;

    @Autowired
    public AddUserMediaCatalogController(UserMediaCatalogRepository userMediaCatalogRepository) {
        this.userMediaCatalogRepository = userMediaCatalogRepository;
    }

    @RequestMapping(value = "/catalog/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaCatalogDTO> addMediaCatalog(@RequestBody MediaCatalogDTO newMediaCatalog) throws Exception {
        Long parentCatalogId = getParentId(newMediaCatalog);
        UserMediaCatalog parentCatalog = userMediaCatalogRepository.findOne(parentCatalogId);

        if (parentCatalog == null) {
            throw new CatalogNotFoundException("Catalog with " + parentCatalogId + " id does not exist. Cannot add new catalog " + newMediaCatalog.getName());
        }

        UserMediaCatalog newUserMediaCatalog = new UserMediaCatalog(newMediaCatalog.getName());
        newUserMediaCatalog.setParentUserMediaCatalog(parentCatalog);

        userMediaCatalogRepository.save(newUserMediaCatalog);

        MediaCatalogDTO mediaCatalogDTO = new MediaCatalogDTOFactory().createWithMedia(newUserMediaCatalog);
        return new ResponseEntity<>(mediaCatalogDTO, HttpStatus.OK);
    }

    private Long getParentId(MediaCatalogDTO newMediaCatalog) {
        MediaCatalogDTO parent = newMediaCatalog.getParent();
        return parent == null ? null : parent.getId();
    }
}
