package com.valychbreak.mymedialib.controller;

import com.valychbreak.mymedialib.data.MediaShortDetails;
import com.valychbreak.mymedialib.data.UserDetailsImpl;
import com.valychbreak.mymedialib.entity.Media;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserMedia;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 2/25/17.
 */
@RestController
public class UserController {

    private UserRepository userRepository;
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    public UserController(UserRepository userRepository, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userRepository = userRepository;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
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
        User createdUser = userRepository.save(user);

        inMemoryUserDetailsManager.createUser(new UserDetailsImpl(user.getUsername(), user.getPassword()));
        return new ResponseEntity<User>(createdUser, HttpStatus.OK);
    }
}
