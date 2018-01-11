package com.valychbreak.mymedialib.dto.catalog;

import com.google.common.collect.Lists;
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

import static com.valychbreak.mymedialib.dto.catalog.MediaCatalogDTOBuilder.aMediaCatalogDTOBuilder;
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

        MediaCatalogDTO mediaCatalogDTO = mediaCatalogDTOFactory.createWithMedia(userMediaCatalog);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(mediaCatalogDTO.getName()).isEqualTo("root_catalog");
        softAssertions.assertThat(mediaCatalogDTO.getId()).isEqualTo(10300L);
        softAssertions.assertAll();
    }

    @Test
    public void createWithoutMediaWith3LevelHierarchy() throws Exception {
        UserMediaCatalog root = new UserMediaCatalog("root");
        UserMediaCatalog child1_1 = new UserMediaCatalog("child1_1");
        UserMediaCatalog child1_2 = new UserMediaCatalog("child1_2");
        UserMediaCatalog child1_2_1 = new UserMediaCatalog("child1_2_1");
        UserMediaCatalog child1_2_2 = new UserMediaCatalog("child1_2_2");
        UserMediaCatalog child1_2_2_1 = new UserMediaCatalog("child1_2_2_1");
        UserMediaCatalog child1_3 = new UserMediaCatalog("child1_3");

        root.getSubUserMediaCatalogs().add(child1_1);
        root.getSubUserMediaCatalogs().add(child1_2);
        root.getSubUserMediaCatalogs().add(child1_3);

        child1_2.getSubUserMediaCatalogs().add(child1_2_1);
        child1_2.getSubUserMediaCatalogs().add(child1_2_2);

        child1_2_2.getSubUserMediaCatalogs().add(child1_2_2_1);

        MediaCatalogDTO mediaCatalogDTO = mediaCatalogDTOFactory.createWithoutMedia(root);

        MediaCatalogDTO expectedChild1_2_2_1 = createCategoryWith("child1_2_2_1");
        MediaCatalogDTO expectedChild1_2_2 = createCategoryWith("child1_2_2", expectedChild1_2_2_1);
        MediaCatalogDTO expectedChild1_2_1 = createCategoryWith("child1_2_1");
        MediaCatalogDTO expectedChild1_2 = createCategoryWith("child1_2", expectedChild1_2_1, expectedChild1_2_2);
        MediaCatalogDTO expectedChild1_1 = createCategoryWith("child1_1");
        MediaCatalogDTO expectedChild1_3 = createCategoryWith("child1_3");
        MediaCatalogDTO expectedRoot = createCategoryWith("root", expectedChild1_1, expectedChild1_2, expectedChild1_3);

        assertThat(mediaCatalogDTO).isEqualTo(expectedRoot);
    }

    private MediaCatalogDTO createCategoryWith(String name, MediaCatalogDTO... subCatalogs) {
        ArrayList<MediaCatalogDTO> catalogs = Lists.newArrayList(subCatalogs);
        return aMediaCatalogDTOBuilder().withName(name).withSubCatalogs(catalogs).build();
    }

    private MediaCatalogDTO createCategoryWith(String name, MediaFullDetailsImpl mediaFullDetails, MediaCatalogDTO... subCatalogs) {
        ArrayList<MediaCatalogDTO> catalogs = Lists.newArrayList(subCatalogs);
        return aMediaCatalogDTOBuilder().withName(name).withSubCatalogs(catalogs).withMedia(Lists.newArrayList(mediaFullDetails)).build();
    }

    @Test
    public void createByUserMediaCatalog() throws Exception {
        UserMediaCatalog userMediaCatalog = getUserMediaCatalogWithoutSubCatalogs();

        MediaCatalogDTO mediaCatalogDTO = mediaCatalogDTOFactory.createWithMedia(userMediaCatalog);

        MediaCatalogDTO expected = aMediaCatalogDTOBuilder()
                .withName("root")
                .withMedia(Arrays.asList(new MediaFullDetailsImpl()))
                .withSubCatalogs(new ArrayList<>())
                .build();

        assertThat(mediaCatalogDTO).isEqualTo(expected);
    }

    @Test
    public void doesNotIncludeMediaInChildrenCatalogs() throws Exception {
        // Tested on hierarchy of 2 children in depth

        UserMediaCatalog userMediaCatalog = getUserMediaCatalogWithHierarchyLevel2();

        MediaCatalogDTO mediaCatalogDTO = mediaCatalogDTOFactory.createWithMedia(userMediaCatalog);

        MediaCatalogDTO childLevel2 = createCategoryWith("child2");
        MediaCatalogDTO childLevel1 = createCategoryWith("child1", childLevel2);
        MediaCatalogDTO expected = createCategoryWith("root", new MediaFullDetailsImpl(), childLevel1);

        childLevel1.setParent(expected);
        childLevel2.setParent(childLevel1);

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