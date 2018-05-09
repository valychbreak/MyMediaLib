package com.valychbreak.mymedialib.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.testtools.OAuth2TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
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

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup("/data/db/common/TestUser.xml")
public class OAuth2AuthorizationTest extends AbstractControllerSecurityTest {

    private static final String TEST_USER = "test_user";
    private static final String USER_PASSWORD = "test12";

    @Autowired
    private OAuth2TestHelper oAuth2TestHelper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void prepare() throws Exception {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void simpleAccessTestApplyingToken() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + requestToken());

        mvc.perform(get("/api/users/search").param("q", "test").headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("*.password").doesNotExist());
    }

    @Test
    public void errorWhenTokenIsInvalid() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer BBasdf123-asdfasdf");

        mvc.perform(get("/api/users/search").requestAttr("q", "test").headers(headers))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("invalid_token"))
                .andExpect(jsonPath("$.error_description").value("Invalid access token: BBasdf123-asdfasdf"));
    }

    @Test
    public void returnsToken() throws Exception {
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
    public void returnsUnauthorizedForBadCredentials() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", "WrongUsername");
        map.add("password", "worngPassword");

        mockMvc.perform(post("/oauth/token").with(httpBasic("gigy","secret"))
                .headers(headers).params(map))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.error", is("invalid_grant")))
                .andExpect(jsonPath("$.error_description", is("Bad credentials")));
    }

    private String requestToken() {
        return oAuth2TestHelper.createAccessToken(TEST_USER, USER_PASSWORD, "ROLE_USER").getValue();
    }
}
