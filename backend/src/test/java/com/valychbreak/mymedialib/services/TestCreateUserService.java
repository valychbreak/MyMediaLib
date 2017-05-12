package com.valychbreak.mymedialib.services;

import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.Role;
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
        Role role = createRole(roleName);

        String username = makeUnique("test");
        String email = makeUnique("testEmail@t.com");
        User user = createUserService.saveUser(username, "test12", "test", email, role);
        User dbUser = userRepository.findFirstByUsername(username);

        Assert.assertEquals(user, dbUser);
    }

    @Test
    public void testCreateSimpleUser() throws Exception {
        String username = makeUnique("test");
        String email = makeUnique("testEmail@t.com");
        User user = createUserService.saveSimpleUser(username, "test12", "test", email);
        User dbUser = userRepository.findFirstByUsername(username);

        Assert.assertEquals(user, dbUser);
    }

    @Test
    public void testCreateAdminUser() throws Exception {
        String username = makeUnique("test");
        String email = makeUnique("testEmail") + "@t.com";
        User user = createUserService.saveAdminUser(username, "test12", "test", email);
        User dbUser = userRepository.findFirstByUsername(username);

        Assert.assertEquals(user, dbUser);
    }

    @Test
    public void testCreateUserInstance() throws Exception {
        String username = makeUnique("test");
        String email = makeUnique("testEmail") + "@t.com";
        User user = createUserService.createUserInstance(username, "test12", "test", email, new Role("role"));

        Assert.assertNotNull(user.getRootUserMediaCatalog());
    }

    private Role createRole(String roleName) {
        Role role = new Role(roleName);
        userRoleRepository.save(role);
        return role;
    }

    private String makeUnique(String string) {
        return string + new Date().getTime();
    }
}
