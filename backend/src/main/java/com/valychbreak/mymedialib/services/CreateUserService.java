package com.valychbreak.mymedialib.services;

import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by valyc on 5/12/2017.
 */
@Service
public class CreateUserService {


    private UserRepository userRepository;
    private UserMediaCollectionRepository userMediaCollectionRepository;
    private UserRoleRepository userRoleRepository;


    @Autowired
    public CreateUserService(UserRepository userRepository, UserMediaCollectionRepository userMediaCollectionRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userMediaCollectionRepository = userMediaCollectionRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public User saveSimpleUser(String username, String password, String name, String email) {
        Role role = getRole(Role.USER_ROLE_NAME);
        return saveUser(username, password, name, email, role);
    }

    public User saveAdminUser(String username, String password, String name, String email) {
        Role role = getRole(Role.ADMIN_ROLE_NAME);
        return saveUser(username, password, name, email, role);
    }

    public User saveUser(String username, String password, String name, String email, Role role) {
        User user = createUserInstance(username, password, name, email, role);
        userMediaCollectionRepository.save(user.getRootUserMediaCollection());
        userRepository.save(user);
        return user;
    }

    public User createUserInstance(String username, String password, String name, String email, Role role) {
        UserMediaCollection userMediaCollection = new UserMediaCollection(username + "_root_catalog");
        User user = new User(username, password, name, email, role);
        user.setRootUserMediaCollection(userMediaCollection);
        return user;
    }

    private Role getRole(String userRoleName) {
        return userRoleRepository.findByRole(userRoleName);
    }
}
