package com.valychbreak.mymedialib.controller;

import com.valychbreak.mymedialib.dto.LoginDTO;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.services.AuthenticationService;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
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

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<User> authenticate(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        User user = authenticationService.authenticate(loginDTO, response);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/signin2", method = RequestMethod.POST)
    public ResponseEntity<OAuth2AccessToken> authenticate2(@RequestBody LoginDTO loginDTO, HttpServletRequest request) throws IOException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // TODO: is there any way to get rid of setting auth to global context?
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        OAuth2AccessToken accessToken = restTemplate(loginDTO).getAccessToken();
        String requestToken = accessToken.getValue();

        //OAuth2AuthenticationManager
        //userInfoTokenServices.loadAuthentication(requestToken);
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
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

    public OAuth2RestTemplate restTemplate(LoginDTO loginDTO) {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();

        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resource(loginDTO.getUsername(), loginDTO.getPassword()), new DefaultOAuth2ClientContext(atr));
        //oAuth2RestTemplate.setAccessTokenProvider(new AccessTokenProviderChain());
        return oAuth2RestTemplate;
    }

    public String requestToken(String username, String password, String requestURI) throws IOException {
        HttpHeaders headers = createHeaders("gigy", "secret");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();

        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> stringResponseEntity = new RestTemplate().postForEntity(
                requestURI + "/oauth/token",
                request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(stringResponseEntity.getBody());
        JsonNode accessToken = jsonNode.get("access_token");

        return accessToken.asText();
    }

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
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
