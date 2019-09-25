package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTO;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOFactory;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
public class UserMediaCollectionControllerTest extends ControllerTest {
    @Autowired
    private UserMediaCollectionRepository userMediaCollectionRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MediaCollectionDTOFactory mediaCollectionDTOFactory;

    @Test
    @DatabaseSetup("/data/db/UserMediaCatalogsForMediaCatalogControllerTest.xml")
    @Transactional
    public void getCollectionWithoutMedia() throws Exception {
        UserMediaCollection userMediaCollection = userMediaCollectionRepository.findById(1000L).orElse(null);

        User user = userRepository.findFirstByUsername("test");
        MediaCollectionDTO expectedMediaCollectionDTO = mediaCollectionDTOFactory.createWithoutMedia(userMediaCollection, user);

        mockMvc.perform(
                get("/api/collection/1000").requestAttr("media", "false")
                        .principal(new TestingAuthenticationToken("test", "test12")))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedMediaCollectionDTO)));
    }

    @Test
    @DatabaseSetup("/data/db/UserMediaCatalogsForMediaCatalogControllerTest.xml")
    @Transactional
    public void getCollectionWithMedia() throws Exception {
        UserMediaCollection userMediaCollection = userMediaCollectionRepository.findById(1000L).orElse(null);

        User user = userRepository.findFirstByUsername("test");
        MediaCollectionDTO expectedMediaCollectionDTO = mediaCollectionDTOFactory.createWithMedia(userMediaCollection, user);

        mockMvc.perform(
                get("/api/collection/1000").param("media", "true")
                        .principal(new TestingAuthenticationToken("test", "test12")))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedMediaCollectionDTO)));
    }

    @Test
    @DatabaseSetup("/data/db/UserMediaCollectionsForMediaCatalogControllerTest-all-collections.xml")
    @Transactional
    public void getAllCollection() throws Exception {

        mockMvc.perform(
                get("/api/user/collection/all").param("media", "true")
                        .principal(new TestingAuthenticationToken("test", "test12")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("collection 1", "collection 2")));
    }
}