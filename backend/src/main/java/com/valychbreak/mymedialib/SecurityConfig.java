package com.valychbreak.mymedialib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 2/25/17.
 */
@RestController
@EnableOAuth2Client
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http
                .authorizeRequests()
                .antMatchers("/css*//**", "/index").permitAll()
                .antMatchers("/signup").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/signin").failureUrl("/login-error");*/

        http
                .anonymous().authorities("ROLE_ANONYMOUS")
                .and().authorizeRequests()
                /*.antMatchers("/local/login").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/api/signin").permitAll()
                .antMatchers("/api/signin2").anonymous()
                .antMatchers("/api/user/add").permitAll()
                .antMatchers("/api/users/search").authenticated()
                .antMatchers("/").permitAll()*/
                .antMatchers("/favicon.ico").permitAll()
                //.antMatchers("/api/**").authenticated()
                .and()
                .csrf()
                .disable()
                /*.headers()
                .frameOptions().disable().and()*/.logout()
                .logoutSuccessUrl("/").permitAll().and().csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new SimpleCORSFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    @ConfigurationProperties("local")
    public ClientResources local() {
		/*ClientResources clientResources = new ClientResources();
		clientResources.getClient().setClientId("gigy");

		return clientResources;*/
        return new ClientResources();
    }

    @Bean
    public OAuth2RestTemplate localClientTemplate() {
        OAuth2RestTemplate template = new OAuth2RestTemplate(local().getClient(), oauth2ClientContext);
        return template;
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        /*filters.add(ssoFilter(facebook(), "/login/facebook"));
        filters.add(ssoFilter(github(), "/login/github"));*/
        filters.add(ssoFilter(local(), "/login/local"));
        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(
                path);

        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setRestTemplate(template);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                client.getResource().getUserInfoUri(), client.getClient().getClientId());
        tokenServices.setRestTemplate(template);
        filter.setTokenServices(tokenServices);
        return filter;
    }
}
