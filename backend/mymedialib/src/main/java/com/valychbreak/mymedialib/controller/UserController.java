package com.valychbreak.mymedialib.controller;

import com.valychbreak.mymedialib.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 2/25/17.
 */
@RestController
public class UserController {
    @RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "test1", "first1", "last1", "test1@t.com"));
        users.add(new User(2L, "test2", "first2", "last2", "test1@t.com"));
        users.add(new User(3L, "test3", "first3", "last3", "test1@t.com"));

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
}
