package com.valychbreak.mymedialib.controller;

import com.google.gson.Gson;
import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.utils.gson.GsonBuilderTools;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations= "classpath:application.yml")
public abstract class ControllerTest {
    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    @Before
    public void init() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                // TODO: use db unit data to create tokens for oauth
                //.apply(springSecurity())
                .build();
    }

    protected String json(Object object) {
        Gson gson = GsonBuilderTools.getGsonBuilder().create();
        return gson.toJson(object);
    }

    protected HttpHeaders authHeaders(String username, String password) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + requestAuthorizationToken(username, password));

        return headers;
    }

    protected String requestAuthorizationToken(String username, String password) throws IOException {
        //return new OAuth2TokenHelper(testRestTemplate).requestToken(username, password);
        return null;
    }
}
