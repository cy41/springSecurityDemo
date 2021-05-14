package com.example.securitydemo.security.jwt;

import com.example.securitydemo.mybatis.service.UserService;
import com.example.securitydemo.security.exception.TokenAuthException;
import com.example.securitydemo.security.pwd.PwdLoginAuthFailureHandler;
import com.example.securitydemo.utils.JwtUtils;
import com.example.securitydemo.utils.RedisService;
import com.example.securitydemo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.securitydemo.CommonConsts.JWT_TOKEN_HEADER;

@Slf4j
@Component
public class JwtAuthRequestHeaderFilter extends OncePerRequestFilter {

    private RequestHeaderRequestMatcher jwtHeaderMatcher = new RequestHeaderRequestMatcher(JWT_TOKEN_HEADER);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthSuccessHandler successHandler;

    @Autowired
    private PwdLoginAuthFailureHandler failureHandler;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            log.info("authentication not null, filter continue");
            filterChain.doFilter(request, response);
            return ;
        }
        if (!jwtHeaderMatcher.matches(request)) {
            log.debug("no jwt header");
            filterChain.doFilter(request, response);
            return ;
        }

        String headerToken = request.getHeader(JWT_TOKEN_HEADER);

        String uidFromToken = StringUtils.safeToString(jwtUtils.parseUidFromToken(headerToken));

        String redisToken = StringUtils.safeToString(redisService.get("jwtCache::jwt_uid_" + uidFromToken));

        log.debug("headerToken {}, uidFromToken {}, redisToken {}", headerToken, uidFromToken, redisToken);

        if (headerToken.equals(redisToken)) {
            UserDetails userDetails = userService.queryUserDetailsById(uidFromToken);
            if (userDetails == null) {
                failureHandler.onAuthenticationFailure(request, response, new UsernameNotFoundException("no such user"));
            }

            Authentication authentication = new JwtAuthToken(userDetails.getAuthorities(), uidFromToken, headerToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            successHandler.onAuthenticationSuccess(request, response, authentication);

        } else {
            failureHandler.onAuthenticationFailure(request, response, new TokenAuthException("token auth error"));
        }
        filterChain.doFilter(request, response);
    }
}
