package com.example.securitydemo.security.sms.handler;

import com.example.securitydemo.security.ResultBean;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SmsAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());

        ResultBean resultBean = new ResultBean(-1, exception.getMessage(), "");
        String json = new Gson().toJson(resultBean, ResultBean.class);
        PrintWriter out = response.getWriter();
        out.write(json);
        out.flush();
        out.close();
    }
}
