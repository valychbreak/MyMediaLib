package com.valychbreak.mymedialib;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.Application;
import org.assertj.core.api.SoftAssertions;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import org.springframework.security.test.context.support.WithAnonymousUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(MyTestsConfiguration.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup("/data/db/common/TestUser.xml")
public class AuthorizationTest {

    public static final String TEST_USER = "test_user";
    public static final String USER_PASSWORD = "test12";
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void prepare() {
		this.mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
    }

    @Ignore
    @Test
    @WithAnonymousUser
    public void returnsPrincipal() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + requestToken());

        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity("/me", new HttpEntity<String>("", headers), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void returnsToken() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", TEST_USER);
        map.add("password", USER_PASSWORD);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> stringResponseEntity = testRestTemplate.withBasicAuth("gigy", "secret").postForEntity(
                "/oauth/token",
                request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(stringResponseEntity.getBody());
        JsonNode accessToken = jsonNode.get("access_token");

        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(accessToken).isNotNull();
    }

    @Test
    public void returnsUnauthorizedForBadCredentials() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", "WrongUsername");
        map.add("password", "worngPassword");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> stringResponseEntity = testRestTemplate.withBasicAuth("gigy", "secret").postForEntity(
                "/oauth/token",
                request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(stringResponseEntity.getBody());
        JsonNode errorResponse = jsonNode.get("error");
        JsonNode errorDescription = jsonNode.get("error_description");

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        softAssertions.assertThat(errorResponse.asText()).isEqualTo("invalid_grant");
        softAssertions.assertThat(errorDescription.asText()).isEqualTo("Bad credentials");
        softAssertions.assertAll();
    }

    public String requestToken() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", TEST_USER);
        map.add("password", USER_PASSWORD);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> stringResponseEntity = testRestTemplate.withBasicAuth("gigy", "secret").postForEntity(
                "/oauth/token",
                request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(stringResponseEntity.getBody());
        JsonNode accessToken = jsonNode.get("access_token");

        return accessToken.asText();
    }
}
