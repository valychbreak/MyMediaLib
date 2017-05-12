package com.valychbreak.mymedialib.controller;

import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

/**
 * Created by valych on 4/28/17.
 */

public class TestUserController extends AbstractControllerTest {


    @Test
    public void testGetAllUsers() throws Exception {
        initUsers();
        Iterable<User> allUsers = userRepository.findAll();
        String expectedResult = json(allUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(expectedResult))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddUser() throws Exception {
        Role role = getUserRole();
        User newUser = new User("username1", "password2", "User name", "useremail@t.com", role);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/add").contentType(MediaType.APPLICATION_JSON).content(json(newUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<User> dbUser = userRepository.findByUsername("username1");
        Assert.assertNotNull(dbUser);
    }

    @Override
    protected void setupTest() {
        //initUsers();
    }



    private void initUsers() {
        Role adminRole = getAdminRole();
        Role role = getUserRole();

        User admin = new User("anotherAdmin", "test12","Another Admin", "t@t.com", adminRole);
        User user = new User("test", "test12","Test", "t2t.com", role);

        userRepository.save(admin);
        userRepository.save(user);
    }

    private Role getAdminRole() {
        return userRoleRepository.findByRole("ADMIN");
    }

    private Role getUserRole() {
        Role role = userRoleRepository.findByRole("USER");
        return role;
    }
}
