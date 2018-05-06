package com.valychbreak.mymedialib.dto.collection;

import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.services.media.MediaDetailsProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOBuilder.aMediaCollectionDTOBuilder;


@Service
public class MediaCollectionDTOFactory {

    private MediaDetailsProvider mediaDetailsProvider;

    public MediaCollectionDTOFactory(MediaDetailsProvider mediaDetailsProvider) {
        this.mediaDetailsProvider = mediaDetailsProvider;
    }

    @Deprecated
    public MediaCollectionDTO createWithMedia(UserMediaCollection userMediaCollection) throws IOException {
        return createWithMedia(userMediaCollection, null);
    }

    public MediaCollectionDTO createWithMedia(UserMediaCollection userMediaCollection, User owner) throws IOException {
        return create(userMediaCollection, owner, true);
    }

    @Deprecated
    public MediaCollectionDTO createWithoutMedia(UserMediaCollection userMediaCollection) throws IOException {
        return createWithoutMedia(userMediaCollection, null);
    }

    public MediaCollectionDTO createWithoutMedia(UserMediaCollection userMediaCollection, User owner) throws IOException {
        return create(userMediaCollection, owner, false);
    }

    protected MediaCollectionDTO create(UserMediaCollection userMediaCollection, User owner, boolean includeMedia) throws IOException {
        List<MediaFullDetailsImpl> mediaList = null;

        if(includeMedia) {
            mediaList = new ArrayList<>();
            for (UserMedia userMedia : userMediaCollection.getUserMediaList()) {
                mediaList.add((MediaFullDetailsImpl) mediaDetailsProvider.getDetails(userMedia.getMedia()));
            }
        }

        return aMediaCollectionDTOBuilder()
                .withId(userMediaCollection.getId())
                .withName(userMediaCollection.getName())
                .withMediaList(mediaList)
                .withOwner(new UserDTO(owner))
                .build();
    }
}
