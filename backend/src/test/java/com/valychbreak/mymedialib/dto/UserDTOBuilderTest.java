package com.valychbreak.mymedialib.dto;

import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDTOBuilderTest {

    private static final String USERNAME = "username";
    private static final String USER_FULL_NAME = "User full name";
    private static final String USER_EMAIL = "email@t.com";
    private static final Long ROLE_ID = 32L;

    @Mock
    private User user;

    @Test
    public void shouldBuildUserDtoFromUserObject() {
        when(user.getUsername()).thenReturn(USERNAME);
        when(user.getName()).thenReturn(USER_FULL_NAME);
        when(user.getEmail()).thenReturn(USER_EMAIL);

        Role role = mock(Role.class);
        when(user.getRole()).thenReturn(role);
        when(role.getId()).thenReturn(ROLE_ID);

        UserDTO userDTO = UserDTOBuilder.aUserDtoBuilderFromUser(user).build();

        assertThat(userDTO.getUsername()).isEqualTo(USERNAME);
        assertThat(userDTO.getName()).isEqualTo(USER_FULL_NAME);
        assertThat(userDTO.getEmail()).isEqualTo(USER_EMAIL);
        assertThat(userDTO.getRoleId()).isEqualTo(ROLE_ID);
    }
}