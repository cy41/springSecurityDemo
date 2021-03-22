package com.example.securitydemo.controllers;

import com.example.securitydemo.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    private PasswordUtils passwordUtils;

    @RequestMapping("/testPwd")
    public String testPwd(String pwd) {
        return passwordUtils.encode(pwd);
    }


}
