package com.example.securitydemo.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthToken extends AbstractAuthenticationToken {

    private final Object uid;

    private Object token;

    public JwtAuthToken(Object uid, Object token) {
        super(null);
        this.uid = uid;
        this.token = token;
        //初始化完成，但是还未认证
        setAuthenticated(false);
    }

    public JwtAuthToken(Collection<? extends GrantedAuthority> authorities, Object uid, Object token) {
        super(authorities);
        this.uid = uid;
        this.token = token;
        setAuthenticated(true);
    }

    // token
    @Override
    public Object getCredentials() {
        return token;
    }

    // uid
    @Override
    public Object getPrincipal() {
        return uid;
    }
}
