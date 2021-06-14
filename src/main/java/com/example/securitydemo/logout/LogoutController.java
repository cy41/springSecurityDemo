package com.example.securitydemo.logout;

import com.example.securitydemo.network.BaseResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private LogoutService logoutService;

    @RequestMapping("/")
    public BaseResponseData logout(@RequestHeader(name = "jwt-token", value = "") String token) {
        if (logoutService.logout(token)) {
            return BaseResponseData.success(true);
        } else {
            return BaseResponseData.failed(true);
        }

    }
}
