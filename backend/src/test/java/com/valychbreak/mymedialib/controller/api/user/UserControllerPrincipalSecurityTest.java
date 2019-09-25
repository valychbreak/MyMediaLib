package com.valychbreak.mymedialib.controller.api.user;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.AbstractControllerSecurityTest;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/data/db/common/TestUser.xml")
public class UserControllerPrincipalSecurityTest extends AbstractControllerSecurityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnUserInfo() throws Exception {
        User user = userRepository.findFirstByUsername("test_user");
        assertThat(user).isNotNull();

        mockMvc.perform(
                get("/api/user/principal")
                        .with(bearerToken("test_user", "test12")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("test_user")))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.email", is("testuser1@t.com")))
                .andExpect(jsonPath("$.roleId", is(10)));
    }
}
