package com.valychbreak.mymedialib.services;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by valych on 4/1/17.
 */
@Service
public class AuthenticationService {

    public static final String APP_COOKIE = "someCookie";


    private UserRepository userRepository;
    private UserDetailsService userDetailsService;

    /**
     * Fixes the issue with cycle dependency with SecurityConfig
     */
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    public AuthenticationService(UserRepository userRepository, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    public User authenticate(LoginDTO loginDTO, HttpServletResponse response) {
        List<User> users = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        User user = users.size() > 0 ? users.get(0) : null;

        if(user != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Cookie cookie = new Cookie(APP_COOKIE, user.getUsername());
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
            //Cookie cannot be accessed via JavaScript
            //cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        return user;
    }

    public void tokenAuthentication(String username){
        UserDetails details = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
