package com.example.securitydemo.utils;

import com.example.securitydemo.cloud.SmsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class PhoneSmsService {

    @Autowired
    private RedisService service;

    @Autowired
    private SmsTemplate smsTemplate;

    private static final int EXPIRED_TIME = 60 * 3;

    private static final String SUFFIX = "verify_phone_";


    public String getVerifyCode(String phone) {
        return (String) service.get(SUFFIX + phone);
    }

    public boolean verifyCode(String phone, String code) {
        String verifyCode = getVerifyCode(phone);
        log.debug("verifyCode is {}", verifyCode);
        if (verifyCode == null) return false;
        return verifyCode.equals(code);
    }


    public String setVerifyCode(String phone) {
        String codeFromRedis = getVerifyCode(phone);
        if (codeFromRedis != null) {
            return codeFromRedis;
        }
        String code = randomCode();
        service.set(SUFFIX + phone, code, EXPIRED_TIME);
        String res = smsTemplate.sendPhone(phone, code);
        log.debug("tx sms res {}", res);
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
