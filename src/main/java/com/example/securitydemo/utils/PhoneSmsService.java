package com.example.securitydemo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

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


    private static final Random RANDOM = new Random();
    private String randomCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

}
