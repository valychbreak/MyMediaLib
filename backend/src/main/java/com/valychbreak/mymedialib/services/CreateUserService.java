package com.valychbreak.mymedialib.services;

import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by valyc on 5/12/2017.
 */
@Service
public class CreateUserService {


    private UserRepository userRepository;
    private UserMediaCollectionRepository userMediaCollectionRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;


    public CreateUserService(UserRepository userRepository, UserMediaCollectionRepository userMediaCollectionRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMediaCollectionRepository = userMediaCollectionRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createSimpleUser(String username, String password, String name, String email) {
        Role role = getRole(Role.USER_ROLE_NAME);
        return createUser(username, password, name, email, role);
    }

    public User createAdminUser(String username, String password, String name, String email) {
        Role role = getRole(Role.ADMIN_ROLE_NAME);
        return createUser(username, password, name, email, role);
    }

    public User createUser(String username, String password, String name, String email, Role role) {
        User user = createUserInstance(username, password, name, email, role);
        userRepository.save(user);
        return user;
    }

    public User createUserInstance(String username, String password, String name, String email, Role role) {
        String encodedPassword = passwordEncoder.encode(password);
        return new User(username, encodedPassword, name, email, role);
    }

    private Role getRole(String userRoleName) {
        return userRoleRepository.findByRole(userRoleName);
    }
}
