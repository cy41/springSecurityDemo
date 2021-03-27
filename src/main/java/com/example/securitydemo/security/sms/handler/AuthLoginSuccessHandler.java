package com.example.securitydemo.security.sms.handler;

import com.example.securitydemo.mybatis.service.UserService;
import com.example.securitydemo.security.ResultBean;
import com.example.securitydemo.utils.JwtUtils;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
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
public class AuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Writer writer = response.getWriter();

        int uid = userService.queryUidByPhone(authentication.getPrincipal().toString());
        String token = jwtUtils.generateToken(uid, DateTime.now().plusWeeks(1).toDate());

        Result result = new Result(200, "success", token);

        String json = new Gson().toJson(result, Result.class);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class Result {
        private int code;
        private String message;
        private String token;
    }
}
