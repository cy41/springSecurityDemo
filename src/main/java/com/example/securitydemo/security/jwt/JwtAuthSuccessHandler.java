package com.example.securitydemo.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //todo success progress
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("success handler start");
        /*PrintWriter out = response.getWriter();
        out.write("success");
        out.flush();
        out.close();*/
        System.out.println(request.getRequestURI());
        request.setAttribute("uid", authentication.getPrincipal());
    }
}
