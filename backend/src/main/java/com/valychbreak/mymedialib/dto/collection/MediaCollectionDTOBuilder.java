package com.valychbreak.mymedialib.dto.collection;

import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.entity.User;

import java.util.List;

public class MediaCollectionDTOBuilder {
    private Long id;
    private String name;
    private List<MediaFullDetailsImpl> mediaList;
    private UserDTO owner;

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

    public MediaCollectionDTOBuilder withMediaList(List<MediaFullDetailsImpl> mediaList) {
        this.mediaList = mediaList;
        return this;
    }

    public MediaCollectionDTOBuilder withOwner(UserDTO user) {
        this.owner = user;
        return this;
    }

    public MediaCollectionDTO build() {
        return new MediaCollectionDTO(id, name, mediaList, owner);
    }
}