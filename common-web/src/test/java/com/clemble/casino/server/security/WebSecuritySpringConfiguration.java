package com.clemble.casino.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by mavarazy on 8/2/14.
 */
@Configuration
@EnableWebSecurity
public class WebSecuritySpringConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.
            inMemoryAuthentication().
            withUser("user").password("password").
            roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            authorizeRequests().
            anyRequest().
            authenticated().
            and().
            formLogin().
            and().
            sessionManagement().
            maximumSessions(1).
            expiredUrl("/login?expired");
    }
}
