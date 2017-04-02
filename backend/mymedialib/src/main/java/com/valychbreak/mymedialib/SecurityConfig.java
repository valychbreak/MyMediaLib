package com.valychbreak.mymedialib;

import com.valychbreak.mymedialib.config.security.XAuthTokenConfigurer;
import com.valychbreak.mymedialib.data.UserDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import com.valychbreak.mymedialib.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 2/25/17.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationService authenticationService;


    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService/*, AuthenticationService authenticationService*/) {
        this.userDetailsService = userDetailsService;
        //this.authenticationService = authenticationService;
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
                .authorizeRequests()
                /*.antMatchers("/signin").permitAll()*/
                .antMatchers("/login").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/").anonymous()
                .antMatchers("/favicon.ico").anonymous()
                .antMatchers("/api/**").authenticated()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().formLogin()
                .loginPage("/login").failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .permitAll().
                and()
                .apply(authTokenConfigurer());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.userDetailsService(inMemoryUserDetailsManager());*/
        auth.userDetailsService(userDetailsService);
    }

    private XAuthTokenConfigurer authTokenConfigurer(){
        return new XAuthTokenConfigurer(authenticationService);
    }

    /*@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userDetailsList.add(new UserDetailsImpl(user.getUsername(), user.getPassword()));
        }
        return new InMemoryUserDetailsManager(userDetailsList);
    }*/
}
