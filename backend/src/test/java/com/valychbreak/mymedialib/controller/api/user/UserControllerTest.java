package com.valychbreak.mymedialib.controller.api.user;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by valych on 4/28/17.
 */

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
public class UserControllerTest extends ControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup(value = "/data/db/UsersForUserControlTest.xml")
    public void getAllUsers() throws Exception {
        Iterable<User> allUsers = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        allUsers.forEach(user -> userDTOList.add(new UserDTO(user)));
        String expectedResult = json(userDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedResult))
                .andExpect(status().isOk());
    }

    @Test
    @DatabaseSetup(value = "/data/db/UsersForUserControlTest.xml")
    public void getAllUsersDoesNotContainPassword() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("*.password").doesNotExist())
                .andExpect(status().isOk());
    }

    @Test
    @DatabaseSetup(value = "/data/db/UsersForUserControlTest.xml")
    public void getUserDetailsById() throws Exception {
        User user = userRepository.findOne(1000L);
        UserDetailsDTO expectedDetails = new UserDetailsDTO(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/details/1000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(json(expectedDetails)));
    }

    @Test
    @DatabaseSetup(value = "/data/db/UsersForUserControlTest.xml")
    public void getUserDetailsByUsername() throws Exception {
        User user = userRepository.findOne(1002L);
        UserDetailsDTO expectedDetails = new UserDetailsDTO(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/details/test2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(json(expectedDetails)));
    }
}
