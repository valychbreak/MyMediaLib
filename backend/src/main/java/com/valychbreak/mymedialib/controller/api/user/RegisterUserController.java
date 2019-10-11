package com.valychbreak.mymedialib.controller.api.user;

import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.dto.NewUserDTO;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRoleRepository;
import com.valychbreak.mymedialib.services.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.valychbreak.mymedialib.dto.UserDTOBuilder.aUserDtoBuilderFromUser;

@RestController
public class RegisterUserController extends APIController {

    private UserRoleRepository userRoleRepository;
    private CreateUserService createUserService;

    @Autowired
    public RegisterUserController(UserRoleRepository userRoleRepository, CreateUserService createUserService) {
        this.userRoleRepository = userRoleRepository;
        this.createUserService = createUserService;
    }

    @PostMapping(value = "/user/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> addUser(@RequestBody NewUserDTO user) {
        Role role = userRoleRepository.findByRole(Role.USER_ROLE_NAME);

        User createdUser = createUserService.createUser(
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                role
        );

        UserDTO createdUserDto = aUserDtoBuilderFromUser(createdUser).build();
        return new ResponseEntity<>(createdUserDto, HttpStatus.OK);
    }
}
