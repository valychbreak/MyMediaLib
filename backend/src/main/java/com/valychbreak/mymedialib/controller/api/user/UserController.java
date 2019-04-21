package com.valychbreak.mymedialib.controller.api.user;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.dto.UserDetailsDTO;
import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.exception.MyMediaLibException;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import com.valychbreak.mymedialib.services.CreateUserService;
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
public class UserController extends APIController {
    private UserRoleRepository userRoleRepository;


    @Autowired
    public UserController(UserRepository userRepository, UserRoleRepository userRoleRepository, CreateUserService createUserService) {
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

            userRoleRepository.saveAll(roles);
        }

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        userRepository.findAll().forEach(user -> userDTOList.add(new UserDTO(user)));
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/details/{userIdOrUsername}")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable String userIdOrUsername) throws MyMediaLibException {
        User user = null;
        try {
            long id = Long.parseLong(userIdOrUsername);
            user = userRepository.findById(id)
                    .orElseThrow(() -> new MyMediaLibException("User with [username/id: " + userIdOrUsername + "was not found"));
        } catch (NumberFormatException e) {
            user = userRepository.findFirstByUsername(userIdOrUsername);
        }

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(user);
        return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
    }
}
