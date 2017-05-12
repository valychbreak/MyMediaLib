package com.valychbreak.mymedialib.controller;

import com.google.gson.Gson;
import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import com.valychbreak.mymedialib.services.CreateUserService;
import com.valychbreak.mymedialib.tools.gson.GsonBuilderTools;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by valych on 4/28/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:test.properties")
public abstract class AbstractControllerTest {

    public static final String ADMIN_ROLE_NAME = "ADMIN";
    public static final String USER_ROLE_NAME = "USER";


    protected User adminUser;
    protected MockMvc mockMvc;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserRoleRepository userRoleRepository;

    @Autowired
    protected CreateUserService createUserService;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void init() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        createAdminUserIfNotExists();
        setupTest();
    }

    private void createAdminUserIfNotExists() {
        adminUser = userRepository.findFirstByUsername("admin");

        if(adminUser == null) {
            Role adminRole = userRoleRepository.findByRole(ADMIN_ROLE_NAME);
            adminUser = createUserInDb("admin", "test12", "Admin", "admin@t.com", adminRole);
        }
    }

    protected User createUserInDb(String username) {
        String password = username + "pass";
        String name = username + "_name";
        String email = username + "@email.com";
        return createUserInDb(username, password, name, email);
    }

    protected User createUserInDb(String username, String password, String name, String email) {
        Role role = userRoleRepository.findByRole(USER_ROLE_NAME);
        return createUserInDb(username, password, name, email, role);
    }

    protected User createUserInDb(String username, String password, String name, String email, Role role) {
        User user = createUserService.saveUser(username, password, name, email, role);
        return user;
    }

    protected void setupTest() throws Exception {

    }

    protected String json(Object object) {
        Gson gson = GsonBuilderTools.getGsonBuilder().create();
        return gson.toJson(object);
    }
}
