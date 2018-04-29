package com.valychbreak.mymedialib.controller.api.user;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.MyTestsConfiguration;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@Import(MyTestsConfiguration.class)
public class UserSearchControllerTest extends ControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/data/db/UsersForUserSearchControllerTest.xml")
    public void searchUsersAndExpectPasswordToBeAbsent() throws Exception {
        User user1 = userRepository.findFirstByUsername("test1");
        User user2 = userRepository.findFirstByUsername("username2");

        HttpHeaders headers = authHeaders("username3", "test12");

        List<UserDTO> expectedResult = Arrays.asList(new UserDTO(user1), new UserDTO(user2));
        mockMvc.perform(get("/api/users/search").param("q", "test").headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedResult), false))
                .andExpect(jsonPath("*.password").doesNotExist());
    }

}