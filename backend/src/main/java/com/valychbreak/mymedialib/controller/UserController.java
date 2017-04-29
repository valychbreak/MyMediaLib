package com.valychbreak.mymedialib.controller;

import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserRole;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 2/25/17.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;


    @Autowired
    public UserController(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @RequestMapping(value = "/initroles", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<List<UserRole>> initRoles() {
        List<UserRole> userRoles = new ArrayList<>();
        if(userRoleRepository.count() == 0) {
            UserRole adminRole = new UserRole("ADMIN");
            UserRole userRole = new UserRole("USER");
            userRoles.add(adminRole);
            userRoles.add(userRole);
            //userRoleRepository.save(adminRole);
            userRoleRepository.save(userRoles);
        }

        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
        UserRole role = userRoleRepository.findByRole("ADMIN");
        user.setRole(role);
        User createdUser = userRepository.save(user);

        //inMemoryUserDetailsManager.createUser(new UserDetailsImpl(user.getUsername(), user.getPassword()));
        return new ResponseEntity<User>(createdUser, HttpStatus.OK);
    }
}
