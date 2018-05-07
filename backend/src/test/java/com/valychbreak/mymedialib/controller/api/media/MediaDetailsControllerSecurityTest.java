package com.valychbreak.mymedialib.controller.api.media;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.valychbreak.mymedialib.controller.AbstractControllerSecurityTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by valych on 9/16/17.
 */
// TODO: use manually created MediaFullDetails objects to compare with API ones
@TestExecutionListeners({WithSecurityContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
//@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
//@DatabaseSetup(value = "/data/db/common/TestUser.xml", type = DatabaseOperation.INSERT)
@TestPropertySource(locations = "classpath:test.yml")
public class MediaDetailsControllerSecurityTest extends AbstractControllerSecurityTest {

    @Autowired
    private WebApplicationContext webapp;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webapp)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }


    @Test
    public void returnsMediaDetailsWhenUserAuthorized() throws Exception {
        RequestPostProcessor bearerToken = bearerToken("gigy");
        mockMvc.perform(
                get("/api/media/details/tt0137523").with(bearerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", notNullValue()));
    }

    @Test
    public void returnsUnauthorizedErrorWhenUserIsNotLoggedIn() throws Exception {

        mockMvc.perform(get("/api/media/details/tt0108778"))
                .andExpect(status().isUnauthorized());
    }
}