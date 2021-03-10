package com.example.securitydemo.security.sms.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

//短信验证码token
public class SmsLoginAuthenticationToken extends AbstractAuthenticationToken {

    private final Object phone;
    private Object verifyCode;

    public SmsLoginAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.phone = principal;
        this.verifyCode = credentials;
        //初始化完成，但是还未认证
        setAuthenticated(false);
    }

    public SmsLoginAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentials) {
        super(authorities);
        this.phone = principal;
        this.verifyCode = credentials;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return verifyCode;
    }

    @Override
    public Object getPrincipal() {
        return phone;
    }
}
