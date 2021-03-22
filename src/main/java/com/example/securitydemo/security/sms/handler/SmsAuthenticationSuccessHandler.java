package com.example.securitydemo.security.sms.handler;

import com.example.securitydemo.security.ResultBean;
import com.google.gson.Gson;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Component
public class SmsAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Writer writer = response.getWriter();
        ResultBean resultBean = new ResultBean(200, "success");
        String json = new Gson().toJson(resultBean, ResultBean.class);
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
