package com.valychbreak.mymedialib.dto.collection;

import com.google.common.collect.Lists;
import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOBuilder.aMediaCollectionDTOBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MediaCollectionDTOFactoryTest {

    private MediaCollectionDTOFactory mediaCollectionDTOFactory;

    @Before
    public void setUp() throws Exception {
        mediaCollectionDTOFactory = new MediaCollectionDTOFactory();
    }

    @Test
    public void factoryCreatesWithCorrectNameAndId() throws Exception {
        UserMediaCollection userMediaCollection = new UserMediaCollection("root_catalog");
        userMediaCollection.setId(10300L);

        MediaCollectionDTO mediaCollectionDTO = mediaCollectionDTOFactory.createWithMedia(userMediaCollection);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(mediaCollectionDTO.getName()).isEqualTo("root_catalog");
        softAssertions.assertThat(mediaCollectionDTO.getId()).isEqualTo(10300L);
        softAssertions.assertAll();
    }

    @Test
    public void createByUserMediaCollection() throws Exception {
        UserMediaCollection userMediaCollection = getUserMediaCollectionWithoutSubCollections();

        MediaCollectionDTO mediaCollectionDTO = mediaCollectionDTOFactory.createWithMedia(userMediaCollection);

        MediaCollectionDTO expected = aMediaCollectionDTOBuilder()
                .withName("root")
                .withMediaList(Arrays.asList(new MediaFullDetailsImpl()))
                .build();

        assertThat(mediaCollectionDTO).isEqualTo(expected);
    }

    private UserMediaCollection getUserMediaCollectionWithoutSubCollections() throws IOException, OMDBException {
        Media media = mock(Media.class);
        when(media.getDetails()).thenReturn(new MediaFullDetailsImpl());

        UserMedia userMedia = mock(UserMedia.class);
        when(userMedia.getMedia()).thenReturn(media);

        UserMediaCollection userMediaCollection = new UserMediaCollection("root");
        userMediaCollection.getUserMediaList().add(userMedia);
        return userMediaCollection;
    }

}