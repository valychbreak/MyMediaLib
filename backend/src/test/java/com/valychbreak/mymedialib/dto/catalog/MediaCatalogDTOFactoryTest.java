package com.valychbreak.mymedialib.dto.catalog;

import com.omertron.omdbapi.OMDBException;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MediaCatalogDTOFactoryTest {

    private MediaCatalogDTOFactory mediaCatalogDTOFactory;

    @Before
    public void setUp() throws Exception {
        mediaCatalogDTOFactory = new MediaCatalogDTOFactory();
    }

    @Test
    public void factoryCreatesWithCorrectNameAndId() throws Exception {
        UserMediaCatalog userMediaCatalog = new UserMediaCatalog("root_catalog");
        userMediaCatalog.setId(10300L);

        MediaCatalogDTO mediaCatalogDTO = mediaCatalogDTOFactory.create(userMediaCatalog);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(mediaCatalogDTO.getName()).isEqualTo("root_catalog");
        softAssertions.assertThat(mediaCatalogDTO.getId()).isEqualTo(10300L);
        softAssertions.assertAll();
    }

    @Test
    public void createByUserMediaCatalog() throws Exception {
        UserMediaCatalog userMediaCatalog = getUserMediaCatalogWithoutSubCatalogs();

        MediaCatalogDTO mediaCatalogDTO = mediaCatalogDTOFactory.create(userMediaCatalog);
        assertThat(mediaCatalogDTO).isEqualTo(new MediaCatalogDTO(null, "root", Arrays.asList(new MediaFullDetailsImpl()), new ArrayList<>()));
    }

    @Test
    public void createByUserMediaCatalogWith2LevelInSubCatalogs() throws Exception {
        UserMediaCatalog userMediaCatalog = getUserMediaCatalogWithHierarchyLevel2();

        MediaCatalogDTO mediaCatalogDTO = mediaCatalogDTOFactory.create(userMediaCatalog);

        MediaCatalogDTO childLevel2 = new MediaCatalogDTO(null, "child2", Arrays.asList(new MediaFullDetailsImpl()), new ArrayList<>());
        MediaCatalogDTO childLevel1 = new MediaCatalogDTO(null, "child1", new ArrayList<>(), Arrays.asList(childLevel2));
        MediaCatalogDTO expected = new MediaCatalogDTO(null, "root", Arrays.asList(new MediaFullDetailsImpl()), Arrays.asList(childLevel1));

        assertThat(mediaCatalogDTO).isEqualTo(expected);
    }
    private UserMediaCatalog getUserMediaCatalogWithoutSubCatalogs() throws IOException, OMDBException {
        Media media = mock(Media.class);
        when(media.getDetails()).thenReturn(new MediaFullDetailsImpl());

        UserMedia userMedia = mock(UserMedia.class);
        when(userMedia.getMedia()).thenReturn(media);

        UserMediaCatalog userMediaCatalog = new UserMediaCatalog("root");
        userMediaCatalog.getUserMediaList().add(userMedia);
        return userMediaCatalog;
    }

    private UserMediaCatalog getUserMediaCatalogWithHierarchyLevel2() throws IOException, OMDBException {
        Media media = mock(Media.class);
        when(media.getDetails()).thenReturn(new MediaFullDetailsImpl());

        UserMedia userMedia = mock(UserMedia.class);
        when(userMedia.getMedia()).thenReturn(media);

        UserMediaCatalog root = new UserMediaCatalog("root");
        root.getUserMediaList().add(userMedia);

        UserMediaCatalog child1 = new UserMediaCatalog("child1");


        UserMediaCatalog child2 = new UserMediaCatalog("child2");
        Media mediaChild2 = mock(Media.class);
        when(mediaChild2.getDetails()).thenReturn(new MediaFullDetailsImpl());

        UserMedia userMediaChild2 = mock(UserMedia.class);
        when(userMediaChild2.getMedia()).thenReturn(mediaChild2);

        child2.getUserMediaList().add(userMediaChild2);

        child1.getSubUserMediaCatalogs().add(child2);
        root.getSubUserMediaCatalogs().add(child1);
        return root;
    }

}