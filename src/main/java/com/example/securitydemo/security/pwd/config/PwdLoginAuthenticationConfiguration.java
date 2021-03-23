package com.example.securitydemo.security.pwd.config;

import com.example.securitydemo.security.pwd.filter.PwdAuthenticationLoginFilter;
import com.example.securitydemo.security.pwd.handler.PwdLoginAuthFailureHandler;
import com.example.securitydemo.security.pwd.service.PwdLoginUserDetailsService;
import com.example.securitydemo.security.sms.filter.SmsLoginAuthenticationFilter;
import com.example.securitydemo.security.sms.handler.AuthLoginSuccessHandler;
import com.example.securitydemo.security.sms.service.SmsLoginUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PwdLoginAuthenticationConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SmsLoginUserDetailsService userDetailsService;

    @Autowired
    private AuthLoginSuccessHandler successHandler;

    @Autowired
    private PwdLoginAuthFailureHandler failureHandler;


    public void configure(HttpSecurity builder) {
        log.debug("start config");
        PwdAuthenticationLoginFilter filter = new PwdAuthenticationLoginFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);

        builder.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        builder.authenticationProvider(provider);
    }
}
