package com.valychbreak.mymedialib.dto.catalog;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.valychbreak.mymedialib.dto.catalog.MediaCatalogDTOBuilder.aMediaCatalogDTOBuilder;


public class MediaCatalogDTOFactory {
    public MediaCatalogDTO createWithMedia(UserMediaCatalog userMediaCatalog) throws IOException, OMDBException {
        return create(userMediaCatalog, true, true);
    }

    public MediaCatalogDTO createWithoutMedia(UserMediaCatalog userMediaCatalog) throws IOException, OMDBException {
        return create(userMediaCatalog, false, true);
    }

    protected MediaCatalogDTO create(UserMediaCatalog userMediaCatalog, boolean includeMedia, boolean includeSubCategories) throws IOException, OMDBException {
        List<MediaFullDetailsImpl> mediaList = null;

        if(includeMedia) {
            mediaList = new ArrayList<>();
            for (UserMedia userMedia : userMediaCatalog.getUserMediaList()) {
                mediaList.add((MediaFullDetailsImpl) userMedia.getMedia().getDetails());
            }
        }

        List<MediaCatalogDTO> subCatalogs = null;
        if (includeSubCategories) {
            subCatalogs = new ArrayList<>();
            for (UserMediaCatalog subCatalog : userMediaCatalog.getSubUserMediaCatalogs()) {
                MediaCatalogDTO mediaCatalogDTO = create(subCatalog, false, true);
                subCatalogs.add(mediaCatalogDTO);
            }
        }

        MediaCatalogDTO parentCatalogDTO = null;

        if (userMediaCatalog.getParentUserMediaCatalog() != null) {
            parentCatalogDTO = create(userMediaCatalog.getParentUserMediaCatalog(), false, false);
        }

        return aMediaCatalogDTOBuilder()
                .withId(userMediaCatalog.getId())
                .withName(userMediaCatalog.getName())
                .withParent(parentCatalogDTO)
                .withMedia(mediaList)
                .withSubCatalogs(subCatalogs)
                .build();
    }
}
