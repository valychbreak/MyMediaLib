package com.valychbreak.mymedialib.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
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
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.valychbreak.mymedialib.entity.Role.ADMIN_ROLE_NAME;
import static com.valychbreak.mymedialib.entity.Role.USER_ROLE_NAME;

/**
 * Created by valych on 4/28/17.
 */
@Deprecated
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations= "classpath:application.yml")
@TestExecutionListeners({WithSecurityContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public abstract class AbstractControllerTest {

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
        Role adminRole = userRoleRepository.findByRole(ADMIN_ROLE_NAME);
        Role role = userRoleRepository.findByRole(USER_ROLE_NAME);

        if (adminRole == null) {
            userRoleRepository.save(new Role(ADMIN_ROLE_NAME));
        }

        if (role == null) {
            userRoleRepository.save(new Role(USER_ROLE_NAME));
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
        User user = createUserService.createUser(username, password, name, email, role);
        return user;
    }

    protected String json(Object object) {
        Gson gson = GsonBuilderTools.getGsonBuilder().create();
        return gson.toJson(object);
    }
}
