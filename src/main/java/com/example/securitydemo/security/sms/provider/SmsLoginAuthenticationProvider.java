package com.example.securitydemo.security.sms.provider;

import com.example.securitydemo.security.sms.service.SmsLoginUserDetailsService;
import com.example.securitydemo.security.sms.token.SmsLoginAuthenticationToken;
import com.example.securitydemo.utils.PhoneSmsService;
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

    @Autowired
    private PhoneSmsService phoneSmsService;

    /**
     * 先验证
     * 再注册
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("start authenticate");
        Assert.isInstanceOf(SmsLoginAuthenticationToken.class, authentication, "not mapping type of token");

        SmsLoginAuthenticationToken authenticationToken = (SmsLoginAuthenticationToken) authentication;

        String phone = (String) authenticationToken.getPrincipal();
        String verifyCode = (String) authenticationToken.getCredentials();

        UserDetails userDetails;

        //verify code error
        if (!phoneSmsService.verifyCode(phone, verifyCode)) {
            log.debug("verify code error");
            throw new AuthenticationCredentialsNotFoundException("verify code error");
        }

        try {
            userDetails = userDetailsService.loadUserByUsername(phone);
        } catch (UsernameNotFoundException exception) {
            // 不会找不到，因为登陆与注册不区分
            log.debug(exception.getMessage());
            throw new InternalAuthenticationServiceException("cannot get user info");
        }

        return new SmsLoginAuthenticationToken(userDetails.getAuthorities(), phone, verifyCode);
    }


    public boolean supports(Class<?> authentication) {
        boolean res = authentication.isAssignableFrom(SmsLoginAuthenticationToken.class);
        log.debug("support " + res);
        return res;
    }
}
