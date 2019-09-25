package com.valychbreak.mymedialib.controller;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/data/db/common/TestUser.xml")
public class AuthenticationControllerTest extends AbstractControllerSecurityTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldRevokeAccessTokenWhenLogout() throws Exception {
        String accessToken = accessTokenProvider.obtainAccessToken("test_user", "test12");

        assertThat(jdbcTemplate.queryForList("select * from oauth_access_token where user_name = 'test_user'")).isNotEmpty();

        mockMvc.perform(
                get("/api/logout")
                        .with(mockRequest -> {
                            mockRequest.addHeader("Authorization", "Bearer " + accessToken);
                            return mockRequest;
                        }))
                .andExpect(status().isOk());

        assertThat(jdbcTemplate.queryForList("select * from oauth_access_token where user_name = 'test_user'")).isEmpty();
    }
}