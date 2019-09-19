package com.valychbreak.mymedialib;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http/*.requiresChannel().anyRequest().requiresSecure().and()*/
                .antMatcher("/me")
                .antMatcher("/api/**")
                //.antMatcher("/oauth/**")
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/api/user/add").permitAll()
                .anyRequest().hasRole("USER")/*.and().headers().frameOptions().disable()*/;//.and().cors();
        // @formatter:on
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(OAuth2Config.RESOURCE_ID);
    }
}
