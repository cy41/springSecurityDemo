package com.example.securitydemo.controllers;

import com.example.securitydemo.utils.PhoneSmsService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.securitydemo.CommonConsts.PHONE;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private PhoneSmsService phoneSmsService;

    @RequestMapping("/sendPhone")
    public SmsResponse sendPhone(HttpServletRequest request) throws IOException {
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        String phone = jsonObject.get(PHONE).getAsString();

        return SmsResponse.success(phoneSmsService.setVerifyCode(phone));
    }

    @Data
    @AllArgsConstructor
    private static class SmsResponse {
        private Integer code;
        private String message;
        private String verifyCode;

        public static SmsResponse success(String verifyCode) {
            return new SmsResponse(200, "success", verifyCode);
        }
    }
}
