package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTO;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import static com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOBuilder.aMediaCollectionDTOBuilder;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
public class AddUserMediaCollectionControllerTest extends ControllerTest {

    @Test
    @DatabaseSetup("/data/db/UserMediaCatalogsForMediaCatalogControllerTest.xml")
    @Transactional
    public void addMediaCollection() throws Exception {
        MediaCollectionDTO newMediaCollection = aMediaCollectionDTOBuilder().withName("New Catalog Name").build();
        mockMvc.perform(
                post("/api/collection/add").contentType(MediaType.APPLICATION_JSON).content(json(newMediaCollection))
                        .principal(new TestingAuthenticationToken("test", "test12")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("New Catalog Name")))
                .andExpect(jsonPath("$.mediaList", hasSize(0)))
                .andExpect(jsonPath("$.owner.username", is("test")));
    }

}