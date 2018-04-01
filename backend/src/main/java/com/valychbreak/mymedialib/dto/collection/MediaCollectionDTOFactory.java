package com.valychbreak.mymedialib.dto.collection;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOBuilder.aMediaCollectionDTOBuilder;


public class MediaCollectionDTOFactory {

    @Deprecated
    public MediaCollectionDTO createWithMedia(UserMediaCollection userMediaCollection) throws IOException, OMDBException {
        return createWithMedia(userMediaCollection, null);
    }

    public MediaCollectionDTO createWithMedia(UserMediaCollection userMediaCollection, User owner) throws IOException, OMDBException {
        return create(userMediaCollection, owner, true, true);
    }

    @Deprecated
    public MediaCollectionDTO createWithoutMedia(UserMediaCollection userMediaCollection) throws IOException, OMDBException {
        return createWithoutMedia(userMediaCollection, null);
    }

    public MediaCollectionDTO createWithoutMedia(UserMediaCollection userMediaCollection, User owner) throws IOException, OMDBException {
        return create(userMediaCollection, owner, false, true);
    }

    protected MediaCollectionDTO create(UserMediaCollection userMediaCollection, User owner, boolean includeMedia, boolean includeSubCategories) throws IOException, OMDBException {
        List<MediaFullDetailsImpl> mediaList = null;

        if(includeMedia) {
            mediaList = new ArrayList<>();
            for (UserMedia userMedia : userMediaCollection.getUserMediaList()) {
                mediaList.add((MediaFullDetailsImpl) userMedia.getMedia().getDetails());
            }
        }

        List<MediaCollectionDTO> subCollections = null;
        /*if (includeSubCategories) {
            subCollections = new ArrayList<>();
            for (UserMediaCollection subCollection : userMediaCollection.getSubUserMediaCollections()) {
                MediaCollectionDTO mediaCollectionDTO = create(subCollection, false, true);
                subCollections.add(mediaCollectionDTO);
            }
        }*/

        MediaCollectionDTO parentCollectionDTO = null;

        /*if (userMediaCollection.getParentUserMediaCollection() != null) {
            parentCollectionDTO = create(userMediaCollection.getParentUserMediaCollection(), false, false);
        }*/

        return aMediaCollectionDTOBuilder()
                .withId(userMediaCollection.getId())
                .withName(userMediaCollection.getName())
                .withMediaList(mediaList)
                .withOwner(new UserDTO(owner))
                .build();
    }
}
