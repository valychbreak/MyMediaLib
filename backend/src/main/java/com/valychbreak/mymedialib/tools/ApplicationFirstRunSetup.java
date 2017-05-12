package com.valychbreak.mymedialib.tools;

import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserRole;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import com.valychbreak.mymedialib.services.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 4/29/17.
 */
@Service
public class ApplicationFirstRunSetup {
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    private CreateUserService createUserService;

    @Autowired
    public ApplicationFirstRunSetup(UserRepository userRepository, UserRoleRepository userRoleRepository, CreateUserService createUserService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.createUserService = createUserService;
    }

    public void execute() {
        if(userRoleRepository.count() == 0) {
            List<UserRole> userRoles = new ArrayList<>();
            UserRole adminRole = new UserRole("ADMIN");
            UserRole userRole = new UserRole("USER");
            userRoles.add(adminRole);
            userRoles.add(userRole);

            userRoleRepository.save(userRoles);
        }

        if(userRepository.count() == 0) {
            UserRole adminRole = userRoleRepository.findByRole("ADMIN");
            createUserService.createUser("test", "test12","Admin test", "test@t.com", adminRole);
        }
    }
}
