package com.example.securitydemo.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private JwtAuthProvider provider;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtAuthHeaderFilter filter = new JwtAuthHeaderFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));

        postProcess(filter);

        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        builder.authenticationProvider(provider);

    }
}
