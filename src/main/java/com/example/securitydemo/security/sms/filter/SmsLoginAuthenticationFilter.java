package com.example.securitydemo.security.sms.filter;

import com.example.securitydemo.security.sms.token.SmsLoginAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.securitydemo.CommonConsts.PHONE;
import static com.example.securitydemo.CommonConsts.VERIFY_CODE;

// 短信验证码登陆filter
@Slf4j
public class SmsLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SmsLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/smsLogin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String phone = request.getParameter(PHONE);
        String verifyCode = request.getParameter(VERIFY_CODE);
        if (StringUtils.isBlank(phone)) {
            phone = "";
        }
        if (StringUtils.isBlank(verifyCode)) {
            verifyCode = "";
        }
        System.out.println("user phone=" + phone + " verifyCode="+verifyCode);
        log.debug("user phone=" + phone + " verifyCode="+verifyCode);
        SmsLoginAuthenticationToken token = new SmsLoginAuthenticationToken(phone, verifyCode);
        setDetails(request, token);
        return getAuthenticationManager().authenticate(token);
    }

    protected void setDetails(HttpServletRequest request, SmsLoginAuthenticationToken token) {
        token.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
