package com.valychbreak.mymedialib.controller;

import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
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
    public ResponseEntity<List<Role>> initRoles() {
        List<Role> roles = new ArrayList<>();
        if(userRoleRepository.count() == 0) {
            Role adminRole = new Role("ADMIN");
            Role role = new Role("USER");
            roles.add(adminRole);
            roles.add(role);
            //userRoleRepository.save(adminRole);
            userRoleRepository.save(roles);
        }

        return new ResponseEntity<>(roles, HttpStatus.OK);
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
        Role role = userRoleRepository.findByRole("ADMIN");
        user.setRole(role);
        User createdUser = userRepository.save(user);

        //inMemoryUserDetailsManager.createUser(new UserDetailsImpl(user.getUsername(), user.getPassword()));
        return new ResponseEntity<User>(createdUser, HttpStatus.OK);
    }
}
