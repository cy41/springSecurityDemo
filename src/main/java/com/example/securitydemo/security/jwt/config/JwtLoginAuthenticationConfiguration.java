package com.example.securitydemo.security.jwt.config;

import com.example.securitydemo.security.jwt.filter.JwtAuthenticationLoginFilter;
import com.example.securitydemo.security.jwt.handler.JwtLoginAuthFailureHandler;
import com.example.securitydemo.security.jwt.handler.JwtLoginAuthSuccessHandler;
import com.example.securitydemo.security.jwt.service.JwtLoginUserDetailsService;
import com.example.securitydemo.security.sms.filter.SmsLoginAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtLoginAuthenticationConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtLoginUserDetailsService userDetailsService;

    @Autowired
    private JwtLoginAuthSuccessHandler successHandler;

    @Autowired
    private JwtLoginAuthFailureHandler failureHandler;


    public void configure(HttpSecurity builder) {
        log.debug("start config");
        JwtAuthenticationLoginFilter filter = new JwtAuthenticationLoginFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);

        builder.addFilterAt(filter, SmsLoginAuthenticationFilter.class);
        builder.authenticationProvider(provider);
    }
}
