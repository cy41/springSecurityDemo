package com.example.securitydemo.security.sms.config;

import com.example.securitydemo.security.sms.filter.SmsLoginAuthenticationFilter;
import com.example.securitydemo.security.sms.provider.SmsLoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SmsLoginAuthConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Qualifier("smsAuthenticationSuccessHandler")
    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Qualifier("smsAuthenticationFailureHandler")
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private SmsLoginAuthenticationProvider provider;



    public void configure(HttpSecurity builder) {
        SmsLoginAuthenticationFilter filter = new SmsLoginAuthenticationFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        builder.authenticationProvider(provider);

    }
}
