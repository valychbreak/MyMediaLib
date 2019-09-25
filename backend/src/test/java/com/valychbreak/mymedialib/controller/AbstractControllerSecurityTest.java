package com.valychbreak.mymedialib.controller;

import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.testtools.OAuth2AccessTokenProvider;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application.yml")
public abstract class AbstractControllerSecurityTest {

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();


    @Autowired
    protected WebApplicationContext webapp;

    protected MockMvc mockMvc;

    private OAuth2AccessTokenProvider accessTokenProvider;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webapp)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();

        accessTokenProvider = new OAuth2AccessTokenProvider(mockMvc);
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
