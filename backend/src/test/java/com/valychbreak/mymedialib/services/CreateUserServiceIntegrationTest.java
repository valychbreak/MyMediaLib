package com.valychbreak.mymedialib.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by valyc on 5/12/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestExecutionListeners({WithSecurityContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@TestPropertySource(locations= "classpath:application.yml")
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
public class CreateUserServiceIntegrationTest {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired private UserRepository userRepository;

    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateUserWithCustomRole() throws Exception {
        String roleName = makeUnique("role");
        Role role = createRoleIfNotExists(roleName);

        String username = makeUnique("test");
        String email = makeUnique("testEmail@t.com");
        User user = createUserService.createUser(username, "test12", "test", email, role);
        User dbUser = userRepository.findFirstByUsername(username);

        assertThat(user).isEqualTo(dbUser);
    }

    @Test
    public void testCreateSimpleUser() throws Exception {
        createRoleIfNotExists(Role.USER_ROLE_NAME);

        String username = makeUnique("test");
        String email = makeUnique("testEmail@t.com");
        User user = createUserService.createSimpleUser(username, "test12", "test", email);
        User dbUser = userRepository.findFirstByUsername(username);

        assertThat(user).isEqualTo(dbUser);
        assertThat(passwordEncoder.matches("test12", user.getPassword())).isTrue();
    }

    @Test
    public void testCreateAdminUser() throws Exception {
        createRoleIfNotExists(Role.ADMIN_ROLE_NAME);

        String username = makeUnique("test");
        String email = makeUnique("testEmail") + "@t.com";
        User user = createUserService.createAdminUser(username, "test12", "test", email);
        User dbUser = userRepository.findFirstByUsername(username);

        assertThat(user).isEqualTo(dbUser);
    }

    private Role createRoleIfNotExists(String roleName) {
        Role roleFromDB = userRoleRepository.findByRole(roleName);
        if (roleFromDB == null) {
            roleFromDB = userRoleRepository.save(new Role(roleName));
        }

        return roleFromDB;
    }

    private String makeUnique(String string) {
        return string + new Date().getTime();
    }
}
