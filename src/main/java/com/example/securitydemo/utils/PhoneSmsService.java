package com.example.securitydemo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneSmsService {

    @Autowired
    private RedisService service;

    private static final int EXPIRED_TIME = 60;

    private static final String SUFFIX = "verify_phone_";


    public String getVerifyCode(String phone) {
        return (String) service.get(SUFFIX + phone);
    }

    public boolean verifyCode(String phone, String code) {
        String verifyCode = getVerifyCode(phone);
        if (verifyCode == null) return false;
        return verifyCode.equals(code);
    }


    public String setVerifyCode(String phone) {
        String code = randomCode();
        service.set(SUFFIX + phone, code, EXPIRED_TIME);
        return code;
    }


    private String randomCode() {
        return "1234";
    }

}
