package com.valychbreak.mymedialib.dto.catalog;

import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;

import java.util.List;

public class MediaCollectionDTOBuilder {
    private Long id;
    private String name;
    private MediaCollectionDTO parent;
    private List<MediaFullDetailsImpl> mediaList;
    private List<MediaCollectionDTO> subCollections;

    public static MediaCollectionDTOBuilder aMediaCollectionDTOBuilder() {
        return new MediaCollectionDTOBuilder();
    }

    public MediaCollectionDTOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public MediaCollectionDTOBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MediaCollectionDTOBuilder withParent(MediaCollectionDTO parent) {
        this.parent = parent;
        return this;
    }

    public MediaCollectionDTOBuilder withMediaList(List<MediaFullDetailsImpl> mediaList) {
        this.mediaList = mediaList;
        return this;
    }

    public MediaCollectionDTOBuilder withSubCollections(List<MediaCollectionDTO> subCollections) {
        this.subCollections = subCollections;
        return this;
    }

    public MediaCollectionDTO build() {
        return new MediaCollectionDTO(id, name, parent, mediaList, subCollections);
    }
}