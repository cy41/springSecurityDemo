package com.example.securitydemo.security.pwd;

import com.example.securitydemo.security.exception.TokenAuthException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class PwdLoginAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    @Data
    @AllArgsConstructor
    private static class ExceptionResult {
        private int code;
        private String message;
    }


    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        log.error("用户登录失败, {}",e.getMessage());
        PrintWriter out = response.getWriter();
        ExceptionResult respBean = new ExceptionResult(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        if (e instanceof LockedException) {
            respBean.setMessage("account locked");
        } else if (e instanceof CredentialsExpiredException) {
            respBean.setMessage("pwd expired");
        } else if (e instanceof AccountExpiredException) {
            respBean.setMessage("account expired");
        } else if (e instanceof DisabledException) {
            respBean.setMessage("account Forbided");
        } else if (e instanceof BadCredentialsException) {
            respBean.setMessage("account or pwd error");
        } else if (e instanceof TokenAuthException) {
            respBean.setMessage(e.getMessage());
        } else {
            respBean.setMessage(e.getMessage());
        }
        String json = new Gson().toJson(respBean,ExceptionResult.class);
        out.write(json);
        out.flush();
        out.close();
    }
}
