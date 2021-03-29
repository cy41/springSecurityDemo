package com.example.securitydemo.controllers;

import com.example.securitydemo.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @Autowired
    private PasswordUtils passwordUtils;

    @RequestMapping("/testPwd")
    public String testPwd(String pwd) {
        return passwordUtils.encode(pwd);
    }

    @RequestMapping("/jwt/auth")
    public String testJwtAuth(HttpServletRequest request) {
        System.out.println("jwt/auth controller start");

        return "hello jwt " + request.getAttribute("uid");
    }

}
