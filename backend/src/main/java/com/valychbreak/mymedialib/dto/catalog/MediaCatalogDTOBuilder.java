package com.valychbreak.mymedialib.dto.catalog;

import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;

import java.util.ArrayList;
import java.util.Collection;

public class MediaCatalogDTOBuilder {

    private MediaCatalogDTO mediaCatalogDTO;

    public static MediaCatalogDTOBuilder aMediaCatalogDTOBuilder() {
        return new MediaCatalogDTOBuilder();
    }

    public MediaCatalogDTOBuilder() {
        mediaCatalogDTO = new MediaCatalogDTO();
    }

    public MediaCatalogDTOBuilder withId(Long id) {
        mediaCatalogDTO.setId(id);
        return this;
    }

    public MediaCatalogDTOBuilder withName(String name) {
        mediaCatalogDTO.setName(name);
        return this;
    }

    public MediaCatalogDTOBuilder withParent(MediaCatalogDTO parent) {
        mediaCatalogDTO.setParent(parent);
        return this;
    }

    public MediaCatalogDTOBuilder withSubCatalogs(Collection<MediaCatalogDTO> subCatalogs) {
        mediaCatalogDTO.setSubCatalogs(subCatalogs == null ? null : new ArrayList<>(subCatalogs));
        return this;
    }

    public MediaCatalogDTOBuilder withMedia(Collection<MediaFullDetailsImpl> mediaList) {
        mediaCatalogDTO.setMediaList(mediaList == null ? null : new ArrayList<>(mediaList));
        return this;
    }

    public MediaCatalogDTO build() {
        return mediaCatalogDTO;
    }
}
