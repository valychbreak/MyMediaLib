package com.valychbreak.mymedialib.controller.api.media;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.testtools.MediaUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestExecutionListeners({WithSecurityContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/data/db/common/TestUser.xml", type = DatabaseOperation.INSERT)
public class MediaDetailsControllerTest extends ControllerTest {

    @Autowired
    private MediaUtils mediaUtils;

    @Test
    public void getMediaDetailsByImdbIdOfMovie() throws Exception {
        MediaFullDetails fightClubMovie = mediaUtils.getMediaFullDetailsBy("tt0137523");

        mockMvc.perform(
                get("/api/media/details/tt0137523")
                        .principal(new TestingAuthenticationToken("test_user", "test12")))
                .andExpect(status().isOk())
                .andExpect(content().json(json(fightClubMovie)));
    }

    @Test
    public void getMediaDetailsByImdbIdOfTvShow() throws Exception {
        MediaFullDetails friendsTVSeries = mediaUtils.getMediaFullDetailsBy("tt0108778");

        mockMvc.perform(
                get("/api/media/details/tt0108778")
                        .principal(new TestingAuthenticationToken("test_user", "test12")))
                .andExpect(status().isOk())
                .andExpect(content().json(json(friendsTVSeries)));
    }
}