package com.example.securitydemo.security.jwt;

import com.example.securitydemo.mybatis.service.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.securitydemo.CommonConsts.JWT_TOKEN_HEADER;
import static com.example.securitydemo.CommonConsts.UID;

@Slf4j
public class JwtAuthHeaderFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthHeaderFilter() {
        super(new RequestHeaderRequestMatcher(JWT_TOKEN_HEADER));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String headerToken = request.getHeader(JWT_TOKEN_HEADER);

        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        log.info("body {}", body);
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();
        String uid = json.get(UID).getAsString();
        if (uid == null) {
            uid = "";
        }

        JwtAuthToken authentication = new JwtAuthToken(uid, headerToken);
        return getAuthenticationManager().authenticate(authentication);

    }
}
