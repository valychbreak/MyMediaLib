package com.valychbreak.mymedialib.dto.catalog;

import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;

import java.util.List;

public class MediaCatalogDTOBuilder {
    private Long id;
    private String name;
    private MediaCatalogDTO parent;
    private List<MediaFullDetailsImpl> mediaList;
    private List<MediaCatalogDTO> subCatalogs;

    public static MediaCatalogDTOBuilder aMediaCatalogDTOBuilder() {
        return new MediaCatalogDTOBuilder();
    }

    public MediaCatalogDTOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public MediaCatalogDTOBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MediaCatalogDTOBuilder withParent(MediaCatalogDTO parent) {
        this.parent = parent;
        return this;
    }

    public MediaCatalogDTOBuilder withMediaList(List<MediaFullDetailsImpl> mediaList) {
        this.mediaList = mediaList;
        return this;
    }

    public MediaCatalogDTOBuilder withSubCatalogs(List<MediaCatalogDTO> subCatalogs) {
        this.subCatalogs = subCatalogs;
        return this;
    }

    public MediaCatalogDTO build() {
        return new MediaCatalogDTO(id, name, parent, mediaList, subCatalogs);
    }
}