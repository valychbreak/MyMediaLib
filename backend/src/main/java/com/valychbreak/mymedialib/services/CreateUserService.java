package com.valychbreak.mymedialib.services;

import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserRole;
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
    public static final String ADMIN_ROLE_NAME = "ADMIN";
    public static final String USER_ROLE_NAME = "USER";


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
        UserRole userRole = getRole(USER_ROLE_NAME);
        return saveUser(username, password, name, email, userRole);
    }

    public User saveAdminUser(String username, String password, String name, String email) {
        UserRole userRole = getRole(ADMIN_ROLE_NAME);
        return saveUser(username, password, name, email, userRole);
    }

    public User saveUser(String username, String password, String name, String email, UserRole role) {
        User user = createUserInstance(username, password, name, email, role);
        userMediaCatalogRepository.save(user.getRootUserMediaCatalog());
        userRepository.save(user);
        return user;
    }

    public User createUserInstance(String username, String password, String name, String email, UserRole role) {
        UserMediaCatalog userMediaCatalog = new UserMediaCatalog(username + "_root_catalog");
        User user = new User(username, password, name, email, role);
        user.setRootUserMediaCatalog(userMediaCatalog);
        return user;
    }

    private UserRole getRole(String userRoleName) {
        return userRoleRepository.findByRole(userRoleName);
    }
}
