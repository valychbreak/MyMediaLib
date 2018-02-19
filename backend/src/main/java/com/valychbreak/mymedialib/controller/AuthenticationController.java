package com.valychbreak.mymedialib.controller;

import com.valychbreak.mymedialib.dto.LoginDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by valych on 2/26/17.
 */
@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private UserRepository userRepository;
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<User> authenticate(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        User user = authenticationService.authenticate(loginDTO, response);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/islogged", method = RequestMethod.GET)
    public ResponseEntity<Boolean> authenticate(HttpServletResponse response) {

        Object credentials = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(credentials);

        boolean result = false;
        try {
            String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            List<User> users = userRepository.findByUsername(username);
            result = users.size() > 0; //SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        } catch (ClassCastException e) {

        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout123", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        SecurityContextHolder.getContext().setAuthentication(null);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        Cookie someCookie = new Cookie(AuthenticationService.APP_COOKIE, "");
        someCookie.setPath("/");
        response.addCookie(someCookie);
    }
}
