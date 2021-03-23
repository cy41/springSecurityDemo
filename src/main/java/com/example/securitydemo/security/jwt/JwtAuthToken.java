package com.example.securitydemo.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthToken extends AbstractAuthenticationToken {

    private final Object principal;

    private Object token;

    public JwtAuthToken(Object principal, Object token) {
        super(null);
        this.principal = principal;
        this.token = token;
        //初始化完成，但是还未认证
        setAuthenticated(false);
    }

    public JwtAuthToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object token) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    // token
    @Override
    public Object getPrincipal() {
        return principal;
    }
}
