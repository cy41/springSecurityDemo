package com.example.securitydemo.security.sms.provider;

import com.example.securitydemo.security.sms.service.SmsLoginUserDetailsService;
import com.example.securitydemo.security.sms.token.SmsLoginAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class SmsLoginAuthenticationProvider implements AuthenticationProvider {

    private static final String TAG = "smsLoginAuthProvider";

    @Autowired
    @Qualifier("smsLoginUserDetailsService")
    private SmsLoginUserDetailsService userDetailsService;

    //TODO verify code
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(SmsLoginAuthenticationToken.class, authentication, "not mapping type of token");

        SmsLoginAuthenticationToken authenticationToken = (SmsLoginAuthenticationToken) authentication;

        String phone = (String) authenticationToken.getPrincipal();
        String verifyCode = (String) authenticationToken.getCredentials();

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(phone);
        } catch (UsernameNotFoundException exception) {
            log.debug(exception.getMessage());
            throw new InternalAuthenticationServiceException("cannot get user info");
        }

        //verify code error
        if (!verifyCode.equals("123")) {
            log.debug("verify code error");
            throw new AuthenticationCredentialsNotFoundException("verify code error");
        }
        return new SmsLoginAuthenticationToken(userDetails.getAuthorities(), phone, verifyCode);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean res = authentication.isAssignableFrom(SmsLoginAuthenticationToken.class);
        log.debug("support" + res);
        return res;
    }
}
