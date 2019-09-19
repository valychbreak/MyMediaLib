package com.valychbreak.mymedialib.controller;

import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

/**
 * Created by valych on 2/26/17.
 */
@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private UserRepository userRepository;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    public AuthenticationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/principal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getPrincipal(Authentication authentication) {
        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
            User firstByUsername = userRepository.findFirstByUsername(username);
            return new ResponseEntity<>(new UserDTO(firstByUsername), HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("User principal is not found");
        }
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

    @RequestMapping("/logout")
    public void logout(Principal principal) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        OAuth2AccessToken accessToken = authorizationServerTokenServices.getAccessToken(oAuth2Authentication);
        consumerTokenServices.revokeToken(accessToken.getValue());
    }
}
