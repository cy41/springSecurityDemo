package com.example.securitydemo.security.pwd;

import com.example.securitydemo.security.pwd.PwdAuthenticationLoginFilter;
import com.example.securitydemo.security.pwd.PwdLoginAuthFailureHandler;
import com.example.securitydemo.security.handler.AuthLoginSuccessHandler;
import com.example.securitydemo.security.sms.service.UserDetailsByPhoneService;
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
    private AuthLoginSuccessHandler successHandler;

    @Autowired
    private PwdLoginAuthFailureHandler failureHandler;

    @Autowired
    private UserDetailsByPhoneService userDetailsService;


    public void configure(HttpSecurity builder) {
        log.debug("start config");
        PwdAuthenticationLoginFilter filter = new PwdAuthenticationLoginFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        PwdAuthLoginProvider provider = new PwdAuthLoginProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);

        builder.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        builder.authenticationProvider(provider);
    }
}
