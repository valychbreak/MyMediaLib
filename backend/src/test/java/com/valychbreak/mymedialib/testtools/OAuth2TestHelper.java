package com.valychbreak.mymedialib.testtools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Component
public class OAuth2TestHelper {

    public static final String CLIENT_ID = "gigy";

    @Autowired
    ClientDetailsService clientDetailsService;
    @Autowired
    AuthorizationServerTokenServices tokenservice;

    public OAuth2AccessToken createAccessToken(String username, String password) {
        // Look up authorities, resourceIds and scopes based on clientId
        ClientDetails client = clientDetailsService.loadClientByClientId(CLIENT_ID);
        Collection<GrantedAuthority> authorities = client.getAuthorities();
        Set<String> resourceIds = client.getResourceIds();
        Set<String> scopes = client.getScope();

        // Default values for other parameters
        Map<String, String> requestParameters = Collections.emptyMap();
        boolean approved = true;
        String redirectUrl = null;
        Set<String> responseTypes = Collections.emptySet();
        Map<String, Serializable> extensionProperties = Collections.emptyMap();

        // Create request
        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, CLIENT_ID, authorities, approved, scopes,
                resourceIds, redirectUrl, responseTypes, extensionProperties);

        // Create OAuth2AccessToken
        User userPrincipal = new User(username, password, true, true, true, true, authorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);
        return tokenservice.createAccessToken(auth);
    }
}
