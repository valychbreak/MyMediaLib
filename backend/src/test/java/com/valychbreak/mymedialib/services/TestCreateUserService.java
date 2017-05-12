package com.valychbreak.mymedialib.services;

import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserRole;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * Created by valyc on 5/12/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:test.properties")
public class TestCreateUserService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired private UserRepository userRepository;

    @Autowired
    private CreateUserService createUserService;

    @Test
    public void testCreateUserWithCustomRole() throws Exception {
        String roleName = makeUnique("role");
        UserRole role = createRole(roleName);

        String username = makeUnique("test");
        String email = makeUnique("testEmail@t.com");
        User user = createUserService.createUser(username, "test12", "test", email, role);
        User dbUser = userRepository.findFirstByUsername(username);

        // TODO: check if users are equal
        Assert.assertNotNull(user);
        Assert.assertNotNull(dbUser);
    }

    private String makeUnique(String string) {
        return string + new Date();
    }

    @Test
    public void testCreateSimpleUser() throws Exception {
        String username = makeUnique("test");
        String email = makeUnique("testEmail@t.com");
        User user = createUserService.createSimpleUser(username, "test12", "test", email);
        User dbUser = userRepository.findFirstByUsername(username);

        // TODO: check if users are equal
        Assert.assertNotNull(user);
        Assert.assertNotNull(dbUser);
    }

    private UserRole createRole(String roleName) {
        UserRole role = new UserRole(roleName);
        userRoleRepository.save(role);
        return role;
    }
}
