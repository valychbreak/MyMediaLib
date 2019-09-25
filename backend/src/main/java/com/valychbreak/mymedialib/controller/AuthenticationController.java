package com.valychbreak.mymedialib.controller;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by valych on 2/26/17.
 */
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private AuthorizationServerTokenServices authorizationServerTokenServices;
    private ConsumerTokenServices consumerTokenServices;

    public AuthenticationController(AuthorizationServerTokenServices authorizationServerTokenServices, ConsumerTokenServices consumerTokenServices) {
        this.authorizationServerTokenServices = authorizationServerTokenServices;
        this.consumerTokenServices = consumerTokenServices;
    }

    @RequestMapping("/logout")
    public void logout(Principal principal) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        OAuth2AccessToken accessToken = authorizationServerTokenServices.getAccessToken(oAuth2Authentication);
        consumerTokenServices.revokeToken(accessToken.getValue());
    }
}
