package com.example.securitydemo.security.pwd;

import com.example.securitydemo.security.exception.TokenAuthException;
import com.example.securitydemo.security.sms.service.UserDetailsByPhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class PwdAuthLoginProvider implements AuthenticationProvider {


    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PwdLoginAuthToken pwdLoginAuthToken = (PwdLoginAuthToken) authentication;

        String phone = pwdLoginAuthToken.getPrincipal().toString();
        String pwd = pwdLoginAuthToken.getCredentials().toString();

        log.info("phone {}, pwd {}", phone, pwd);

        // 找不到就直接throw UsernameNotFoundException了
        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

        if (!passwordEncoder.matches(pwd, userDetails.getPassword())) {
            throw new BadCredentialsException("password encoder not matchs");
        }

        return new PwdLoginAuthToken(userDetails.getAuthorities(), phone, pwd);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.info("supports {}", authentication.isAssignableFrom(PwdLoginAuthToken.class));
        return authentication.isAssignableFrom(PwdLoginAuthToken.class);
    }
}
