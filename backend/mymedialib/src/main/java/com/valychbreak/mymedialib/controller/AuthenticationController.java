package com.valychbreak.mymedialib.controller;

import com.valychbreak.mymedialib.dto.LoginDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by valych on 2/26/17.
 */
@RestController
public class AuthenticationController {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<User> authenticate(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        List<User> users = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        User user = users.size() > 0 ? users.get(0) : null;

        if(user != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
