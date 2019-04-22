package com.valychbreak.mymedialib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	public static final String RESOURCE_ID = "resource-id";

	private final UserDetailsService userDetailsService;
	private final DataSource dataSource;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
    private final int expiration;

	public OAuth2Config(UserDetailsService userDetailsService,
                        DataSource dataSource,
                        PasswordEncoder passwordEncoder,
                        AuthenticationConfiguration authenticationConfiguration,
                        @Value("${gigy.oauth.tokenTimeout:3600}") int expiration
    ) throws Exception {
		this.userDetailsService = userDetailsService;
		this.dataSource = dataSource;
		this.passwordEncoder = passwordEncoder;
	    this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
	    this.expiration = expiration;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
		configurer
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(new JdbcTokenStore(dataSource));
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource)
                .passwordEncoder(passwordEncoder);
                /*.withClient("gigy")
                .secret("secret")
                .accessTokenValiditySeconds(expiration)
				.scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .resourceIds(RESOURCE_ID);*/
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
				.addTokenEndpointAuthenticationFilter(new SimpleCORSFilter());
	}
}
