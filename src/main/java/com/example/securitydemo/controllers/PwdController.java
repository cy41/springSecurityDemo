package com.example.securitydemo.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PwdController {

    @RequestMapping("pwd/setPwd")
    public BusinessResponse setPwd(@RequestBody BusinessRequest businessRequest) {
        return BusinessResponse.success();
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class BusinessRequest {
        private String phone;
        private String pwd;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class BusinessResponse {
        private Integer code;
        private String message;
        public static BusinessResponse success() {
            return new BusinessResponse(200, "modify success");
        }

        public static BusinessResponse failed() {
            return new BusinessResponse(-1, "failed");
        }
    }
}
