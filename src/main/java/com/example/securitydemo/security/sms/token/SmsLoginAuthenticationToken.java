package com.example.securitydemo.security.sms.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

//短信验证码token
public class SmsLoginAuthenticationToken extends AbstractAuthenticationToken {

    private final Object phone;
    private Object verifyCode;

    public SmsLoginAuthenticationToken(Object phone, Object verifyCode) {
        super(null);
        this.phone = phone;
        this.verifyCode = verifyCode;
        //初始化完成，但是还未认证
        setAuthenticated(false);
    }

    public SmsLoginAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object phone, Object verifyCode) {
        super(authorities);
        this.phone = phone;
        this.verifyCode = verifyCode;
        setAuthenticated(true);
    }


    public Object getCredentials() {
        return verifyCode;
    }


    public Object getPrincipal() {
        return phone;
    }
}
