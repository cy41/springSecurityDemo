package com.example.securitydemo.controllers;

import com.example.securitydemo.network.AuthData;
import com.example.securitydemo.network.BaseResponseData;
import com.example.securitydemo.utils.JwtUtils;
import com.example.securitydemo.utils.RedisService;
import com.example.securitydemo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class JwtAuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/jwt/auth")
    public BaseResponseData testJwtAuth(HttpServletRequest request) {
        System.out.println("jwt/auth controller start");

        return AuthData.success(request.getAttribute("uid").toString());
    }

    @RequestMapping("/jwt/token")
    public BaseResponseData authToken(@RequestParam("token") String token) {

        if (StringUtils.isNullOrEmpty(token)) {
            return AuthData.failed();
        }
        String uidFromToken = StringUtils.safeToString(jwtUtils.parseUidFromToken(token));

        String redisToken = StringUtils.safeToString(redisService.get("jwtCache::jwt_uid_" + uidFromToken));

        log.debug("token {}, uidFromToken {}, redisToken {}", token, uidFromToken, redisToken);

        if (token.equals(redisToken) && StringUtils.isNullOrEmpty(redisToken)) {
            return AuthData.success(uidFromToken);
        } else {
            return AuthData.failed();
        }
    }

}
