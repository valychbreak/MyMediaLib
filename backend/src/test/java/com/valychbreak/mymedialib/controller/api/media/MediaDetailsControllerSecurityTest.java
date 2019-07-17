package com.valychbreak.mymedialib.controller.api.media;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.AbstractControllerSecurityTest;
import org.junit.Test;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestExecutionListeners({WithSecurityContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/data/db/common/TestUser.xml")
public class MediaDetailsControllerSecurityTest extends AbstractControllerSecurityTest {

    @Test
    public void returnsMediaDetailsWhenUserAuthorized() throws Exception {
        mockMvc.perform(
                get("/api/media/details/tt0137523")
                        .with(bearerToken("test_user", "test12")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", notNullValue()));
    }

    @Test
    public void returnsUnauthorizedErrorWhenUserIsNotLoggedIn() throws Exception {

        mockMvc.perform(get("/api/media/details/tt0108778"))
                .andExpect(status().isUnauthorized());
    }
}