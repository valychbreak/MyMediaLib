package com.valychbreak.mymedialib.controller.api.user;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import com.valychbreak.mymedialib.services.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterUserController extends APIController {

    private UserRoleRepository userRoleRepository;
    private CreateUserService createUserService;

    @Autowired
    public RegisterUserController(UserRoleRepository userRoleRepository, CreateUserService createUserService) {
        this.userRoleRepository = userRoleRepository;
        this.createUserService = createUserService;
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
        Role role = userRoleRepository.findByRole(Role.USER_ROLE_NAME);
        user.setRole(role);
        User createdUser = createUserService.saveUser(user.getUsername(), user.getPassword(),
                user.getName(), user.getEmail(), user.getRole());
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }
}
