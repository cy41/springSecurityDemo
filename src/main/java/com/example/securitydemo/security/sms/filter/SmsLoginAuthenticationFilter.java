package com.example.securitydemo.security.sms.filter;

import com.example.securitydemo.security.sms.token.SmsLoginAuthenticationToken;
import com.example.securitydemo.utils.StringUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
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

import static com.example.securitydemo.CommonConsts.PHONE;
import static com.example.securitydemo.CommonConsts.VERIFY_CODE;

// 短信验证码登陆filter
@Slf4j
public class SmsLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SmsLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/sms/login", "POST"));
    }


    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("attempt start");
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        log.info("body {}", body);
        String phone = "";
        String verifyCode = "";

        if (StringUtils.hasText(body)) {
            JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
            phone = jsonObject.get(PHONE).getAsString();
            verifyCode = jsonObject.get(VERIFY_CODE).getAsString();
        }

        log.info("user phone=" + phone + " verifyCode="+verifyCode);
        SmsLoginAuthenticationToken token = new SmsLoginAuthenticationToken(phone, verifyCode);
        setDetails(request, token);
        return getAuthenticationManager().authenticate(token);
    }

    protected void setDetails(HttpServletRequest request, SmsLoginAuthenticationToken token) {
        token.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
