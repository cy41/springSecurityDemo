package com.example.securitydemo.security.jwt;

import com.example.securitydemo.mybatis.service.UserService;
import com.example.securitydemo.security.exception.TokenAuthException;
import com.example.securitydemo.security.pwd.handler.PwdLoginAuthFailureHandler;
import com.example.securitydemo.utils.JwtUtils;
import com.example.securitydemo.utils.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.securitydemo.CommonConsts.JWT_TOKEN_HEADER;

@Slf4j
public class JwtAuthHeaderFilter extends OncePerRequestFilter {

    private RequestHeaderRequestMatcher tokenMather = new RequestHeaderRequestMatcher("jwt-token");

    private AuthenticationManager authenticationManager;

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private PwdLoginAuthFailureHandler failureHandler;

    @Autowired
    private TokenAuthSuccessHeader successHeader;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!tokenMather.matches(request)) {
            log.debug("no jwt-token header, try doFilter");
            filterChain.doFilter(request, response);
            return ;
        }
        String token = request.getHeader(JWT_TOKEN_HEADER);
        if (StringUtils.isBlank(token)) {
            SecurityContextHolder.clearContext();
            failureHandler.onAuthenticationFailure(request, response, new TokenAuthException("no jwt-token header"));
            return ;
        } else {
            Long uid = JwtUtils.getInstance().parseUidFromToken(token);
            if (redisService.get("jwt_" + token) == token || userService.queryUserDetailsById(uid) != null) {

                JwtAuthToken jwtAuthToken = new JwtAuthToken(uid, token);
                Authentication authentication = authenticationManager.authenticate(jwtAuthToken);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                successHeader.onAuthenticationSuccess(request, response, jwtAuthToken);
            }
        }


        filterChain.doFilter(request, response);
    }
}
