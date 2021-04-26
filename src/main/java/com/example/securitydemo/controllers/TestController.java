package com.example.securitydemo.controllers;

import com.example.securitydemo.network.AuthResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @RequestMapping("/jwt/auth")
    public AuthResponse testJwtAuth(HttpServletRequest request) {
        System.out.println("jwt/auth controller start");

        return AuthResponse.success(request.getAttribute("uid").toString());
    }

}
