package com.example.securitydemo.security.pwd.filter;

import com.example.securitydemo.utils.StringUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.securitydemo.CommonConsts.*;

@Slf4j
public class PwdAuthenticationLoginFilter extends AbstractAuthenticationProcessingFilter {

    public PwdAuthenticationLoginFilter() {
        super(new AntPathRequestMatcher("/pwd/login", "POST"));
    }


    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.debug("attempt start");
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        log.info("body {}", body);
        String phone = "";
        String pwd = "";
        if (StringUtils.hasText(body)) {
            JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
            phone = jsonObject.get(PHONE).getAsString().trim();
            pwd = jsonObject.get(PWD).getAsString().trim();
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(phone, pwd);
        log.debug("token is " + token.toString());
        return getAuthenticationManager().authenticate(token);
    }
}
