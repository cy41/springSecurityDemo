package com.example.securitydemo.controllers;

import com.example.securitydemo.network.AuthData;
import com.example.securitydemo.network.BaseResponseData;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class JwtAuthController {

    @RequestMapping("/jwt/auth")
    public BaseResponseData testJwtAuth(HttpServletRequest request) {
        System.out.println("jwt/auth controller start");

        return AuthData.success(request.getAttribute("uid").toString());
    }

}
