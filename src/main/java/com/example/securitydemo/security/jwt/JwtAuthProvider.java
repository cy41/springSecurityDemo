package com.example.securitydemo.security.jwt;

import com.example.securitydemo.mybatis.service.UserService;
import com.example.securitydemo.security.exception.TokenAuthException;
import com.example.securitydemo.utils.JwtUtils;
import com.example.securitydemo.utils.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtAuthProvider implements AuthenticationProvider {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String headerUid = authentication.getPrincipal().toString();
        String headerToken = authentication.getCredentials().toString();
        if (StringUtils.isBlank(headerUid) || StringUtils.isBlank(headerToken)) {
            throw new TokenAuthException("uid or token is null");
        }
        Long uidFromToken = JwtUtils.getInstance().parseUidFromToken(headerToken);
        if (uidFromToken.toString().equals(headerUid) && redisService.get("jwt_uid_" + headerUid) == headerToken) {
            UserDetails userDetails = userService.queryUserDetailsById(uidFromToken);
            if (userDetails == null) {
                throw new UsernameNotFoundException("no such user");
            }
            return new JwtAuthToken(userDetails.getAuthorities() ,headerUid, headerToken);
        }
        throw new TokenAuthException("uid not equals token");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthToken.class);
    }
}
