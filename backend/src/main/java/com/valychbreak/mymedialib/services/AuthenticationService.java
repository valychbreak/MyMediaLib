package com.valychbreak.mymedialib.services;

import com.valychbreak.mymedialib.dto.LoginDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 4/1/17.
 */
@Service
public class AuthenticationService {

    public static final String APP_COOKIE = "someCookie";


    private UserRepository userRepository;
    private UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;


    @Autowired
    public AuthenticationService(UserRepository userRepository, UserDetailsService userDetailsService, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
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

    public OAuth2AccessToken obtainOAuth2AccessToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // TODO: is there any way to get rid of setting auth to global context?
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        OAuth2AccessToken accessToken = restTemplate(username, password).getAccessToken();

        SecurityContextHolder.getContext().setAuthentication(null);
        return accessToken;
    }

    public OAuth2RestTemplate restTemplate(String username, String password) {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();

        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resource(username, password), new DefaultOAuth2ClientContext(atr));
        return oAuth2RestTemplate;
    }

    protected OAuth2ProtectedResourceDetails resource(String username, String password) {

        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();

        List scopes = new ArrayList<String>(2);
        scopes.add("write");
        scopes.add("read");
        resource.setAccessTokenUri("http://localhost:8080/oauth/token");
        resource.setClientId("gigy");
        resource.setClientSecret("secret");
        resource.setGrantType("password");
        resource.setScope(scopes);

        resource.setUsername(username);
        resource.setPassword(password);

        return resource;
    }
}
