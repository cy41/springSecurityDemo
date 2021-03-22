package com.example.securitydemo.security.jwt.handler;

import com.example.securitydemo.security.entitys.MyUserDetails;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class JwtLoginAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //TODO token

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        String generateToken = "123412341234";
        response.setHeader("jwt-token",generateToken);
        log.info("生成token并设置给header: {}",generateToken);
        //登录成功将用户信息返回
        PrintWriter out = response.getWriter();
        //user.setPassword("");
        String userJson = new Gson().toJson(user, MyUserDetails.class);
        out.write(userJson);
        out.flush();
        out.close();
    }
}
