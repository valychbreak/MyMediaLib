package com.valychbreak.mymedialib.services;

import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyUserDetailsServiceTest {

    public static final String USERNAME = "test_user";
    @Mock
    private UserRepository userRepository;

    private UserDetailsService service;

    @Before
    public void setUp() throws Exception {
        User user = new User(USERNAME, "test12", "Test User", "e@t.com", new Role(Role.USER_ROLE_NAME));
        when(userRepository.findFirstByUsername(eq(USERNAME))).thenReturn(user);

        service = new MyUserDetailsService(userRepository);
    }

    @Test
    public void loadUserByUsername() throws Exception {
        UserDetails userDetails = service.loadUserByUsername(USERNAME);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USERNAME);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void returnsNullWhenUserDoesNotExist() throws Exception {
        service.loadUserByUsername("NotExistingUsername");
    }
}