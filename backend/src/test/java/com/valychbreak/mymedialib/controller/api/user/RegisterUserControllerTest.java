package com.valychbreak.mymedialib.controller.api.user;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.dto.NewUserDTO;
import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/data/db/BasicRolesDataset.xml")
public class RegisterUserControllerTest extends ControllerTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldRegisterNewUser() throws Exception {
        Role role = getUserRole();

        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername("username1");
        newUserDTO.setPassword("password2");
        newUserDTO.setName("User name");
        newUserDTO.setEmail("useremail@t.com");

        mockMvc.perform(
                post("/api/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(newUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username1"))
                .andExpect(jsonPath("$.name").value("User name"))
                .andExpect(jsonPath("$.email").value("useremail@t.com"))
                .andExpect(jsonPath("$.roleId").value(role.getId()))
                .andExpect(jsonPath("$.password").doesNotExist());

        assertThat(userRepository.findFirstByUsername("username1")).isNotNull();
    }

    private Role getUserRole() {
        return userRoleRepository.findByRole(Role.USER_ROLE_NAME);
    }
}