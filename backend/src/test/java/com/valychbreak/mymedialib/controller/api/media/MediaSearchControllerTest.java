package com.valychbreak.mymedialib.controller.api.media;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
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
public class MediaSearchControllerTest extends ControllerTest {
    @Test
    public void mediaSearch() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", "batman");
        params.add("media-type", "media");

        mockMvc.perform(
                get("/api/media/search").params(params).principal(new TestingAuthenticationToken("test", "test12"))
                        .principal(new TestingAuthenticationToken("test_user", "test12")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(20)));
    }

    @Test
    public void mediaSearchWithPage() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", "batman");
        params.add("media-type", "media");
        params.add("p", "3");

        mockMvc.perform(
                get("/api/media/search").params(params)
                        .principal(new TestingAuthenticationToken("test_user", "test12")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(3)))
                .andExpect(jsonPath("$.items", hasSize(getItemsSizeMatcher())));
    }

    @Test
    public void favoriteMediaIsMarkedAsFavorite() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", "Spider-Man 3");
        params.add("media-type", "media");

        mockMvc.perform(
                get("/api/media/search").params(params)
                        .principal(new TestingAuthenticationToken("test_user", "test12")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[*].isFavourite", containsInAnyOrder(true, false)));
    }

    // Fixes temporary problem with size: it can be less 20 sometimes (when the item doesn't have a poster)
    private BaseMatcher<Integer> getItemsSizeMatcher() {
        return new BaseMatcher<Integer>() {
            @Override
            public boolean matches(Object o) {
                int sizeValue = (Integer) o;
                return sizeValue > 15 && sizeValue <= 20;
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}
