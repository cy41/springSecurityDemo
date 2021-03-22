package com.example.securitydemo.security.jwt.filter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.securitydemo.CommonConsts.PWD;
import static com.example.securitydemo.CommonConsts.USER_NAME;

@Slf4j
public class JwtAuthenticationLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationLoginFilter() {
        super(new AntPathRequestMatcher("/jwtLogin", "POST"));
    }


    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        log.info(body);
        String name = "";
        String pwd = "";
        if (StringUtils.hasText(body)) {
            JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
            name = jsonObject.get(USER_NAME).getAsString().trim();
            pwd = jsonObject.get(PWD).getAsString().trim();
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(name, pwd);
        log.debug("token is " + token.toString());
        return getAuthenticationManager().authenticate(token);
    }
}
