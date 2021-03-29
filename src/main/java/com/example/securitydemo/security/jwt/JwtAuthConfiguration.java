package com.example.securitydemo.security.jwt;

import com.example.securitydemo.security.pwd.PwdLoginAuthFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private PwdLoginAuthFailureHandler failureHandler;

    @Autowired
    private JwtAuthRequestHeaderFilter filter;

    @Override
    public void configure(HttpSecurity builder) {
        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    }
}
