package com.example.securitydemo.security.sms.handler;

import com.example.securitydemo.mybatis.service.UserService;
import com.example.securitydemo.security.ResultBean;
import com.example.securitydemo.utils.JwtUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Writer writer = response.getWriter();
        ResultBean resultBean = new ResultBean(200, "success");
        Long uid = userService.queryUidByPhone((String) authentication.getPrincipal());
        String token = JwtUtils.getInstance().generateToken(uid, 24 * 60 * 60);
        response.setHeader("jwt-token", token);
        String json = new Gson().toJson(resultBean, ResultBean.class);
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
