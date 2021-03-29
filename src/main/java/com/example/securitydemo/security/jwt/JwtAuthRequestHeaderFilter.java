package com.example.securitydemo.security.jwt;

import com.example.securitydemo.mybatis.service.UserService;
import com.example.securitydemo.security.exception.TokenAuthException;
import com.example.securitydemo.security.pwd.handler.PwdLoginAuthFailureHandler;
import com.example.securitydemo.utils.JwtUtils;
import com.example.securitydemo.utils.RedisService;
import com.example.securitydemo.utils.StringUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import static com.example.securitydemo.CommonConsts.UID;

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
        }
        if (!jwtHeaderMatcher.matches(request)) {
            log.debug("no jwt header");
            filterChain.doFilter(request, response);
            return ;
        }

        String headerToken = request.getHeader(JWT_TOKEN_HEADER);
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        log.info("body {}", body);

        JsonObject json = new JsonParser().parse(body).getAsJsonObject();

        String headerUid = StringUtils.safeToString(json.get(UID).getAsString());

        int uidFromToken = jwtUtils.parseUidFromToken(headerToken);

        String redisToken = StringUtils.safeToString(redisService.get("jwtCache::jwt_uid_" + headerUid));

        log.debug("headerUid {}, headerToken {}, uidFromToken {}, redisToken {}", headerUid, headerToken, uidFromToken, redisToken);

        if (StringUtils.safeToString(uidFromToken).equals(headerUid) && headerToken.equals(redisToken)) {
            UserDetails userDetails = userService.queryUserDetailsById(uidFromToken);
            if (userDetails == null) {
                failureHandler.onAuthenticationFailure(request, response, new UsernameNotFoundException("no such user"));
            }

            Authentication authentication = new JwtAuthToken(userDetails.getAuthorities(), headerUid, headerToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            successHandler.onAuthenticationSuccess(request, response, authentication);

        } else {
            failureHandler.onAuthenticationFailure(request, response, new TokenAuthException("uid not equals token"));
        }
        filterChain.doFilter(request, response);
    }
}
