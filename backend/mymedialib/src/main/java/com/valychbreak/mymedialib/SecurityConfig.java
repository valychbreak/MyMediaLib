package com.valychbreak.mymedialib;

import com.valychbreak.mymedialib.data.UserDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valych on 2/25/17.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
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
                .antMatchers("/signin").permitAll()
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
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager());
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userDetailsList.add(new UserDetailsImpl(user.getUsername(), user.getPassword()));
        }
        return new InMemoryUserDetailsManager(userDetailsList);
    }
}
