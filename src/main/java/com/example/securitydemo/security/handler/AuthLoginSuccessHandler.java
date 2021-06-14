package com.example.securitydemo.security.handler;

import com.example.securitydemo.mybatis.service.UserService;
import com.example.securitydemo.network.BaseResponseData;
import com.example.securitydemo.utils.JwtUtils;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 登陆/注册成功handler，response格式：
 * {
 *     "code": 200,
 *     "message": "success",
 *     "token": ""
 * }
 */
@Component
@Slf4j
public class AuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Writer writer = response.getWriter();

        String phone = authentication.getPrincipal().toString();
        log.info("success handle start {}", phone);
        int uid = userService.queryUidByPhone(phone);
        String token = jwtUtils.generateToken(uid, DateTime.now().plusWeeks(1).toDate());

        BaseResponseData result = Result.success(token, String.valueOf(uid));

        String json = new Gson().toJson(result, BaseResponseData.class);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Result {

        private String token;
        private String uid;

        public static BaseResponseData success(String token, String uid) {
            return BaseResponseData.success(new Result(token, uid));
        }
    }
}
