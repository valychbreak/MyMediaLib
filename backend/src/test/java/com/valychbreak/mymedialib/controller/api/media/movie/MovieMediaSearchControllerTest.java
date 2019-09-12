package com.valychbreak.mymedialib.controller.api.media.movie;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/data/db/MediaSearchControllerTest.xml")
public class MovieMediaSearchControllerTest extends ControllerTest {

    @Test
    public void movieSearch() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", "batman");
        params.add("media-type", "movie");

        mockMvc.perform(
                get("/api/media/search").params(params)
                        .principal(new TestingAuthenticationToken("test_user", "test12")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(20)))
                .andExpect(jsonPath("$.items[*].type", hasItem("Movie")));
    }

    @Test
    public void movieSearchSetsFavoriteToTrue() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", "Spider-Man 3");
        params.add("media-type", "movie");

        mockMvc.perform(
                get("/api/media/search").params(params)
                        .principal(new TestingAuthenticationToken("test_user", "test12")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[*].isFavourite", everyItem(isOneOf(true, false))));
    }

}