package com.valychbreak.mymedialib.services;

import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import com.valychbreak.mymedialib.repository.UserMediaCatalogRepository;
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
    private UserMediaCatalogRepository userMediaCatalogRepository;
    private UserRoleRepository userRoleRepository;


    @Autowired
    public CreateUserService(UserRepository userRepository, UserMediaCatalogRepository userMediaCatalogRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userMediaCatalogRepository = userMediaCatalogRepository;
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
        userMediaCatalogRepository.save(user.getRootUserMediaCatalog());
        userRepository.save(user);
        return user;
    }

    public User createUserInstance(String username, String password, String name, String email, Role role) {
        UserMediaCatalog userMediaCatalog = new UserMediaCatalog(username + "_root_catalog");
        User user = new User(username, password, name, email, role);
        user.setRootUserMediaCatalog(userMediaCatalog);
        return user;
    }

    private Role getRole(String userRoleName) {
        return userRoleRepository.findByRole(userRoleName);
    }
}
