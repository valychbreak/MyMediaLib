package com.valychbreak.mymedialib.controller;

import com.google.gson.Gson;
import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserRole;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 4/28/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:test.properties")
public abstract class AbstractControllerTest {

    protected User adminUser;
    protected MockMvc mockMvc;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserRoleRepository userRoleRepository;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        initUserRoles();
        createAdminUser();
        setupTest();
    }

    private void createAdminUser() {
        UserRole adminRole = userRoleRepository.findByRole("ADMIN");
        adminUser = new User("admin", "test12","Admin", "admin@t.com", adminRole);
        userRepository.save(adminUser);
    }

    protected void setupTest() {

    }

    protected String json(Object object) {
        Gson gson = GsonBuilderTools.getGsonBuilder().create();
        return gson.toJson(object);
    }

    private void initUserRoles() {
        List<UserRole> userRoles = new ArrayList<>();
        UserRole adminRole = new UserRole("ADMIN");
        UserRole userRole = new UserRole("USER");
        userRoles.add(adminRole);
        userRoles.add(userRole);

        userRoleRepository.save(userRoles);
    }
}
