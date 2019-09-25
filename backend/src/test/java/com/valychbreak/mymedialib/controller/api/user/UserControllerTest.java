package com.valychbreak.mymedialib.controller.api.user;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.AbstractControllerWithoutSecurityTest;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.dto.UserDetailsDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

import static com.valychbreak.mymedialib.testtools.TestUtils.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by valych on 4/28/17.
 */

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/data/db/UsersForUserControlTest.xml")
public class UserControllerTest extends AbstractControllerWithoutSecurityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllUsers() throws Exception {
        Iterable<User> allUsers = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        allUsers.forEach(user -> userDTOList.add(new UserDTO(user)));
        String expectedResult = json(userDTOList);

        mockMvc.perform(get("/api/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsersDoesNotContainPassword() throws Exception {

        mockMvc.perform(get("/api/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("*.password").doesNotExist())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserDetailsById() throws Exception {
        User user = userRepository.findById(1000L).orElse(null);
        UserDetailsDTO expectedDetails = new UserDetailsDTO(user);

        mockMvc.perform(get("/api/user/details/1000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(json(expectedDetails)));
    }

    @Test
    public void getUserDetailsByUsername() throws Exception {
        User user = userRepository.findById(1002L).orElse(null);
        UserDetailsDTO expectedDetails = new UserDetailsDTO(user);

        mockMvc.perform(get("/api/user/details/test2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(json(expectedDetails)));
    }
}
