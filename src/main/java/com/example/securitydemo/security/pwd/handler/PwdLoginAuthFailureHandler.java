package com.example.securitydemo.security.pwd.handler;

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
            respBean.setMessage("账户被锁定，请联系管理员!");
        } else if (e instanceof CredentialsExpiredException) {
            respBean.setMessage("密码过期，请联系管理员!");
        } else if (e instanceof AccountExpiredException) {
            respBean.setMessage("账户过期，请联系管理员!");
        } else if (e instanceof DisabledException) {
            respBean.setMessage("账户被禁用，请联系管理员!");
        } else if (e instanceof BadCredentialsException) {
            respBean.setMessage("用户名或者密码输入错误，请重新输入!");
        }
        String json = new Gson().toJson(respBean,ExceptionResult.class);
        out.write(json);
        out.flush();
        out.close();
    }
}
