package com.valychbreak.mymedialib.controller.api.user;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.exception.MyMediaLibException;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.valychbreak.mymedialib.dto.UserDTOBuilder.aUserDtoBuilderFromUser;

@RestController
public class UserController extends APIController {

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/user/principal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {

            String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            User firstByUsername = userRepository.findFirstByUsername(username);

            UserDTO userDTO = aUserDtoBuilderFromUser(firstByUsername).build();
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("User principal is not found");
        }
    }

    @GetMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserDTO>> getUsers() {

        Iterable<User> allUsers = userRepository.findAll();
        List<UserDTO> userDTOList = StreamSupport.stream(allUsers.spliterator(), false)
                .map((user) -> aUserDtoBuilderFromUser(user).build())
                .collect(Collectors.toList());

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/user/details/{userIdOrUsername}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable String userIdOrUsername) throws MyMediaLibException {
        User user;
        try {
            long id = Long.parseLong(userIdOrUsername);
            user = userRepository.findById(id)
                    .orElseThrow(() -> new MyMediaLibException("User with [username/id: " + userIdOrUsername + "was not found"));
        } catch (NumberFormatException e) {
            user = userRepository.findFirstByUsername(userIdOrUsername);
        }

        UserDTO userDTO = aUserDtoBuilderFromUser(user).build();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
