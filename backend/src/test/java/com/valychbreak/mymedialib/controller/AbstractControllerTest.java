package com.valychbreak.mymedialib.controller;

import com.google.gson.Gson;
import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import com.valychbreak.mymedialib.services.CreateUserService;
import com.valychbreak.mymedialib.utils.gson.GsonBuilderTools;
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
@Deprecated
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations= "classpath:test.yml")
public abstract class AbstractControllerTest {

    public static final String ADMIN_ROLE_NAME = "ADMIN";
    public static final String USER_ROLE_NAME = "USER";


    protected User adminUser;
    protected User user;

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

        initRoles();
        createDefaultUsersIfNotExist();
    }

    private void initRoles() {
        Role adminRole = userRoleRepository.findByRole(Role.ADMIN_ROLE_NAME);
        Role role = userRoleRepository.findByRole(Role.USER_ROLE_NAME);

        if (adminRole == null) {
            userRoleRepository.save(new Role(Role.ADMIN_ROLE_NAME));
        }

        if (role == null) {
            userRoleRepository.save(new Role(Role.USER_ROLE_NAME));
        }
    }

    private void createDefaultUsersIfNotExist() {
        adminUser = userRepository.findFirstByUsername("admin");
        user = userRepository.findFirstByUsername("user");

        if(adminUser == null) {
            Role adminRole = userRoleRepository.findByRole(ADMIN_ROLE_NAME);
            adminUser = createUserInDb("admin", "test12", "Admin", "admin@t.com", adminRole);
        }

        if (user == null) {
            Role userRole = userRoleRepository.findByRole(USER_ROLE_NAME);
            user = createUserInDb("user", "test12", "User", "user@t.com", userRole);
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

    protected String json(Object object) {
        Gson gson = GsonBuilderTools.getGsonBuilder().create();
        return gson.toJson(object);
    }
}
