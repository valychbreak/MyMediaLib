package com.valychbreak.mymedialib.controller.api.user;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.AbstractControllerSecurityTest;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/data/db/common/TestUser.xml")
public class UserControllerPrincipalSecurityTest extends AbstractControllerSecurityTest {

    @Test
    public void shouldReturnUserInfo() throws Exception {
        mockMvc.perform(
                get("/api/user/principal")
                        .with(bearerToken("test_user", "test12")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("test_user")))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.email", is("testuser1@t.com")))
                .andExpect(jsonPath("$.roleId", is(10)));
    }

    @Test
    public void shouldReturnUnauthorizedWhenNoTokenGiven() throws Exception {
        mockMvc.perform(get("/api/user/principal"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error", is("unauthorized")));
    }
}
