package com.valychbreak.mymedialib.controller;

import com.google.gson.Gson;
import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.testtools.OAuth2AccessTokenProvider;
import com.valychbreak.mymedialib.utils.gson.GsonBuilderTools;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application.yml")
public abstract class AbstractControllerSecurityTest {

    @Autowired
    protected WebApplicationContext webapp;

    protected OAuth2AccessTokenProvider accessTokenProvider;

    protected MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webapp)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();

        accessTokenProvider = new OAuth2AccessTokenProvider();
        accessTokenProvider.setMockMvc(mockMvc);
    }

    protected String json(Object object) {
        Gson gson = GsonBuilderTools.getGsonBuilder().create();
        return gson.toJson(object);
    }

    protected RequestPostProcessor bearerToken(String username, String password) {
        return mockRequest -> {
            String token;
            try {
                token = accessTokenProvider.obtainAccessToken(username, password);
            } catch (Exception e) {
                throw new RuntimeException("Failed to obtain an access token", e);
            }
            mockRequest.addHeader("Authorization", "Bearer " + token);
            return mockRequest;
        };
    }
}
