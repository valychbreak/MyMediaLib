package com.valychbreak.mymedialib.controller.api.user;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.valychbreak.mymedialib.dto.UserDTOBuilder.aUserDtoBuilderFromUser;

@RestController
public class UserSearchController extends APIController {

    private UserRepository userRepository;

    @Autowired
    public UserSearchController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/users/search", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam("q") String query) {

        List<User> foundUsers = userRepository.findByUsernameOrNameIgnoreCaseContaining(query, query);
        List<UserDTO> userDTOList = foundUsers.stream()
                .map((user) -> aUserDtoBuilderFromUser(user).build())
                .collect(Collectors.toList());

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
}
