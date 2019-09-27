package com.valychbreak.mymedialib.controller.api.user;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.dto.UserDetailsDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.exception.MyMediaLibException;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 2/25/17.
 */
@RestController
public class UserController extends APIController {

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/user/principal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            User firstByUsername = userRepository.findFirstByUsername(username);
            return new ResponseEntity<>(new UserDTO(firstByUsername), HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("User principal is not found");
        }
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
        User user;
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
