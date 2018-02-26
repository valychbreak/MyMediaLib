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
    public void createWithoutMediaWith3LevelHierarchy() throws Exception {
        UserMediaCollection root = new UserMediaCollection("root");
        UserMediaCollection child1_1 = new UserMediaCollection("child1_1");
        UserMediaCollection child1_2 = new UserMediaCollection("child1_2");
        UserMediaCollection child1_2_1 = new UserMediaCollection("child1_2_1");
        UserMediaCollection child1_2_2 = new UserMediaCollection("child1_2_2");
        UserMediaCollection child1_2_2_1 = new UserMediaCollection("child1_2_2_1");
        UserMediaCollection child1_3 = new UserMediaCollection("child1_3");

        root.getSubUserMediaCollections().add(child1_1);
        root.getSubUserMediaCollections().add(child1_2);
        root.getSubUserMediaCollections().add(child1_3);

        child1_2.getSubUserMediaCollections().add(child1_2_1);
        child1_2.getSubUserMediaCollections().add(child1_2_2);

        child1_2_2.getSubUserMediaCollections().add(child1_2_2_1);

        MediaCollectionDTO mediaCollectionDTO = mediaCollectionDTOFactory.createWithoutMedia(root);

        MediaCollectionDTO expectedChild1_2_2_1 = createCollectionWith("child1_2_2_1");
        MediaCollectionDTO expectedChild1_2_2 = createCollectionWith("child1_2_2", expectedChild1_2_2_1);
        MediaCollectionDTO expectedChild1_2_1 = createCollectionWith("child1_2_1");
        MediaCollectionDTO expectedChild1_2 = createCollectionWith("child1_2", expectedChild1_2_1, expectedChild1_2_2);
        MediaCollectionDTO expectedChild1_1 = createCollectionWith("child1_1");
        MediaCollectionDTO expectedChild1_3 = createCollectionWith("child1_3");
        MediaCollectionDTO expectedRoot = createCollectionWith("root", expectedChild1_1, expectedChild1_2, expectedChild1_3);

        assertThat(mediaCollectionDTO).isEqualTo(expectedRoot);
    }

    private MediaCollectionDTO createCollectionWith(String name, MediaCollectionDTO... subCollections) {
        ArrayList<MediaCollectionDTO> collections = Lists.newArrayList(subCollections);
        return aMediaCollectionDTOBuilder().withName(name).withSubCollections(collections).build();
    }

    private MediaCollectionDTO createCollectionWith(String name, MediaFullDetailsImpl mediaFullDetails, MediaCollectionDTO... subCollections) {
        ArrayList<MediaCollectionDTO> collections = Lists.newArrayList(subCollections);
        return aMediaCollectionDTOBuilder().withName(name).withSubCollections(collections).withMediaList(Lists.newArrayList(mediaFullDetails)).build();
    }

    @Test
    public void createByUserMediaCollection() throws Exception {
        UserMediaCollection userMediaCollection = getUserMediaCollectionWithoutSubCollections();

        MediaCollectionDTO mediaCollectionDTO = mediaCollectionDTOFactory.createWithMedia(userMediaCollection);

        MediaCollectionDTO expected = aMediaCollectionDTOBuilder()
                .withName("root")
                .withMediaList(Arrays.asList(new MediaFullDetailsImpl()))
                .withSubCollections(new ArrayList<>())
                .build();

        assertThat(mediaCollectionDTO).isEqualTo(expected);
    }

    @Test
    public void doesNotIncludeMediaInChildrenCollections() throws Exception {
        // Tested on hierarchy of 2 children in depth

        UserMediaCollection userMediaCollection = getUserMediaCollectionWithHierarchyLevel2();

        MediaCollectionDTO mediaCollectionDTO = mediaCollectionDTOFactory.createWithMedia(userMediaCollection);

        MediaCollectionDTO childLevel2 = createCollectionWith("child2");
        MediaCollectionDTO childLevel1 = createCollectionWith("child1", childLevel2);
        MediaCollectionDTO expected = createCollectionWith("root", new MediaFullDetailsImpl(), childLevel1);

        childLevel1.setParent(expected);
        childLevel2.setParent(childLevel1);

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

    private UserMediaCollection getUserMediaCollectionWithHierarchyLevel2() throws IOException, OMDBException {
        Media media = mock(Media.class);
        when(media.getDetails()).thenReturn(new MediaFullDetailsImpl());

        UserMedia userMedia = mock(UserMedia.class);
        when(userMedia.getMedia()).thenReturn(media);

        UserMediaCollection root = new UserMediaCollection("root");
        root.getUserMediaList().add(userMedia);

        UserMediaCollection child1 = new UserMediaCollection("child1");


        UserMediaCollection child2 = new UserMediaCollection("child2");
        Media mediaChild2 = mock(Media.class);
        when(mediaChild2.getDetails()).thenReturn(new MediaFullDetailsImpl());

        UserMedia userMediaChild2 = mock(UserMedia.class);
        when(userMediaChild2.getMedia()).thenReturn(mediaChild2);

        child2.getUserMediaList().add(userMediaChild2);

        child1.getSubUserMediaCollections().add(child2);
        root.getSubUserMediaCollections().add(child1);
        return root;
    }

}