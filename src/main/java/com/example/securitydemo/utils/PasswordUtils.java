package com.example.securitydemo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@DependsOn("myWebSecurityConfiguration")
public class PasswordUtils {

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String encode(String pwd) {
        return passwordEncoder.encode(pwd);
    }



}
