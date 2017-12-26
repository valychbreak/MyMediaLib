package com.valychbreak.mymedialib.dto.catalog;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MediaCatalogDTOFactory {
    public MediaCatalogDTO createWithMedia(UserMediaCatalog userMediaCatalog) throws IOException, OMDBException {
        return create(userMediaCatalog, true, true);
    }

    public MediaCatalogDTO createWithoutMedia(UserMediaCatalog userMediaCatalog) throws IOException, OMDBException {
        return create(userMediaCatalog, false, true);
    }

    protected MediaCatalogDTO create(UserMediaCatalog userMediaCatalog, boolean includeMedia) throws IOException, OMDBException {
        List<MediaFullDetailsImpl> mediaList = null;

        if(includeMedia) {
            mediaList = new ArrayList<>();
            for (UserMedia userMedia : userMediaCatalog.getUserMediaList()) {
                mediaList.add((MediaFullDetailsImpl) userMedia.getMedia().getDetails());
            }
        }

        List<MediaCatalogDTO> subCatalogs = new ArrayList<>();
        for (UserMediaCatalog subCatalog : userMediaCatalog.getSubUserMediaCatalogs()) {
            MediaCatalogDTO mediaCatalogDTO = create(subCatalog, includeMedia);
            subCatalogs.add(mediaCatalogDTO);
        }

        MediaCatalogDTO parentCatalogDTO = null;

        if (userMediaCatalog.getParentUserMediaCatalog() != null) {
            parentCatalogDTO = create(userMediaCatalog.getParentUserMediaCatalog(), false);
        }
        return new MediaCatalogDTO(userMediaCatalog.getId(), userMediaCatalog.getName(), parentCatalogDTO, mediaList, subCatalogs);
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
                MediaCatalogDTO mediaCatalogDTO = create(subCatalog, false, false);
                subCatalogs.add(mediaCatalogDTO);
            }
        }

        MediaCatalogDTO parentCatalogDTO = null;

        if (userMediaCatalog.getParentUserMediaCatalog() != null) {
            parentCatalogDTO = create(userMediaCatalog.getParentUserMediaCatalog(), false, false);
        }
        return new MediaCatalogDTO(userMediaCatalog.getId(), userMediaCatalog.getName(), parentCatalogDTO, mediaList, subCatalogs);
    }
}
