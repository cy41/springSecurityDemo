package com.example.securitydemo.security.pwd;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PwdLoginAuthToken extends AbstractAuthenticationToken {

    private final Object phone;
    private Object pwd;

    public PwdLoginAuthToken(Object phone, Object pwd) {
        super(null);
        this.phone = phone;
        this.pwd = pwd;
        //初始化完成，但是还未认证
        setAuthenticated(false);
    }

    public PwdLoginAuthToken(Collection<? extends GrantedAuthority> authorities, Object phone, Object pwd) {
        super(authorities);
        this.phone = phone;
        this.pwd = pwd;
        setAuthenticated(true);
    }

    public Object getCredentials() {
        return pwd;
    }

    public Object getPrincipal() {
        return phone;
    }
}
