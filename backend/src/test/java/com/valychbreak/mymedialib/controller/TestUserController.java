package com.valychbreak.mymedialib.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserRole;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import com.valychbreak.mymedialib.tools.gson.GsonBuilderTools;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 4/28/17.
 */

public class TestUserController extends AbstractControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Test
    public void testGetAllUsers() throws Exception {
        Iterable<User> allUsers = userRepository.findAll();
        String expectedResult = json(allUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(expectedResult))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddUser() throws Exception {
        UserRole userRole = getUserRole();
        User newUser = new User("username1", "password2", "User name", "useremail@t.com", userRole);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/add").contentType(MediaType.APPLICATION_JSON).content(json(newUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<User> dbUser = userRepository.findByUsername("username1");
        Assert.assertNotNull(dbUser);
    }

    @Override
    protected void setupTest() {
        initUserRoles();
        initUsers();
    }

    private void initUserRoles() {
        List<UserRole> userRoles = new ArrayList<>();
        UserRole adminRole = new UserRole("ADMIN");
        UserRole userRole = new UserRole("USER");
        userRoles.add(adminRole);
        userRoles.add(userRole);

        userRoleRepository.save(userRoles);
    }

    private void initUsers() {
        UserRole adminRole = getAdminRole();
        UserRole userRole = getUserRole();

        User admin = new User("admin", "test12","Admin", "t@t.com", adminRole);
        User user = new User("test", "test12","Test", "t2t.com", userRole);

        userRepository.save(admin);
        userRepository.save(user);
    }

    private UserRole getAdminRole() {
        return userRoleRepository.findByRole("ADMIN");
    }

    private UserRole getUserRole() {
        UserRole userRole = userRoleRepository.findByRole("USER");
        return userRole;
    }
}
