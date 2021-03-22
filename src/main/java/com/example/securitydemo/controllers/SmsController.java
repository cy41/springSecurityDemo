package com.example.securitydemo.controllers;

import com.example.securitydemo.utils.PhoneSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private PhoneSmsService phoneSmsService;

    @RequestMapping("/sendPhone")
    public String sendPhone(String phone) {
        return phoneSmsService.setVerifyCode(phone);
    }
}
