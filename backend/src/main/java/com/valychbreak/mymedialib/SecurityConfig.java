package com.valychbreak.mymedialib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.servlet.Filter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by valych on 2/25/17.
 */
@RestController
@EnableOAuth2Client
@EnableAuthorizationServer
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//@Import(AuthenticationManagerConfiguration.class)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @RequestMapping({ "/user", "/me" })
    public Principal user(Principal principal) {
        return principal;
    }

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
                .antMatchers("/local/login").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/api/signin").permitAll()
                .antMatchers("/api/signin2").anonymous()
                .antMatchers("/api/user/add").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").anonymous()
                .antMatchers("/api/**").authenticated()
                .and()
                .csrf()
                .disable()
                /*.headers()
                .frameOptions().disable().and()*/.logout()
                .logoutSuccessUrl("/").permitAll().and().csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new SimpleCORSFilter(), BasicAuthenticationFilter.class);
                //.cors();
        /*http.anonymous().authorities("ROLE_ANONYMOUS").and()
                .requestMatchers().antMatchers("/login", "/oauth/authorize")
                .and().antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**")
                .permitAll()
                .anyRequest()
                .authenticated().and().exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/#test")).and().logout()
                .logoutSuccessUrl("/").permitAll().and().csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);*/
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
/*
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        *//*auth.userDetailsService(inMemoryUserDetailsManager());*//*
        auth.userDetailsService(userDetailsService);
    }

    private XAuthTokenConfigurer authTokenConfigurer(){
        return new XAuthTokenConfigurer(authenticationService);
    }*/


    /**
     * Fixes the issue with cycle dependency with AuthenticationService
     * (authenticationService -> authenticationManager -> Security config -> authenticationService)
    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }*/
    /*@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userDetailsList.add(new UserDetailsImpl(user.getUsername(), user.getPassword()));
        }
        return new InMemoryUserDetailsManager(userDetailsList);
    }*/
}
