package com.valychbreak.mymedialib.controller;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class LogoutController {

    private AuthorizationServerTokenServices authorizationServerTokenServices;
    private ConsumerTokenServices consumerTokenServices;

    public LogoutController(AuthorizationServerTokenServices authorizationServerTokenServices, ConsumerTokenServices consumerTokenServices) {
        this.authorizationServerTokenServices = authorizationServerTokenServices;
        this.consumerTokenServices = consumerTokenServices;
    }

    @GetMapping("/logout")
    public void logout(Principal principal) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        OAuth2AccessToken accessToken = authorizationServerTokenServices.getAccessToken(oAuth2Authentication);
        consumerTokenServices.revokeToken(accessToken.getValue());
    }
}
