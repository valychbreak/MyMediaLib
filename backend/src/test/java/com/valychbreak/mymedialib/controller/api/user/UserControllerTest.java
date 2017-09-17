package com.valychbreak.mymedialib.controller.api.user;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.valychbreak.mymedialib.Application;
import com.valychbreak.mymedialib.controller.AbstractControllerTest;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by valych on 4/28/17.
 */

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/data/db/UsersForUserControlTest.xml")
//@DatabaseTearDown
public class UserControllerTest extends ControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllUsers() throws Exception {
        Iterable<User> allUsers = userRepository.findAll();
        String expectedResult = json(allUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }
}
