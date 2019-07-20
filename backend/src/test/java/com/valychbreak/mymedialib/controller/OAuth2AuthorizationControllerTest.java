package com.valychbreak.mymedialib.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.testtools.OAuth2AccessTokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application.yml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup("/data/db/OAuth2AuthorizationTest.xml")
public class OAuth2AuthorizationControllerTest {

    private static final String TEST_USER = "test_user";
    private static final String USER_PASSWORD = "test12";

    private OAuth2AccessTokenProvider oAuth2AccessTokenProvider;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void prepare() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        oAuth2AccessTokenProvider = new OAuth2AccessTokenProvider(mockMvc);
    }

    @Test
    public void shouldReturnStatusOkWhenHasValidToken() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + requestToken());

        mockMvc.perform(get("/api/users/search").param("q", "test").headers(headers))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnUnauthorizedWhenAccessAPIWithoutAccessToken() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorizedStatusWithErrorWhenTokenIsInvalid() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer BBasdf123-asdfasdf");

        mockMvc.perform(get("/api/users/search").requestAttr("q", "test").headers(headers))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_token"))
                .andExpect(jsonPath("$.error_description").value("Invalid access token: BBasdf123-asdfasdf"));
    }

    @Test
    public void forbiddenToAccessWhenInvalidRole() throws Exception {
        final String accessToken = oAuth2AccessTokenProvider.obtainAccessToken("user_with_invalid_role", "test12");

        mockMvc.perform(
                get("/api/users")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isForbidden());

    }

    @Test
    public void shouldCreateNewTokenOnRequest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", TEST_USER);
        map.add("password", USER_PASSWORD);

        mockMvc.perform(post("/oauth/token").with(httpBasic("gigy","secret"))
                .headers(headers).params(map))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token", notNullValue()))
                .andExpect(jsonPath("$.token_type", is("bearer")))
                .andExpect(jsonPath("$.expires_in", notNullValue()))
                .andExpect(jsonPath("$.scope", is("read write")));
    }

    @Test
    public void shouldReturnBadRequestForBadCredentials() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", "WrongUsername");
        map.add("password", "wrongPassword");

        mockMvc.perform(post("/oauth/token").with(httpBasic("gigy","secret"))
                .headers(headers).params(map))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid_grant")))
                .andExpect(jsonPath("$.error_description", is("Bad credentials")));
    }

    private String requestToken() throws Exception {
        return oAuth2AccessTokenProvider.obtainAccessToken(TEST_USER, USER_PASSWORD);
    }
}
