package com.example.securitydemo.logout;

import com.example.securitydemo.utils.JwtUtils;
import com.example.securitydemo.utils.RedisService;
import com.example.securitydemo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtUtils jwtUtils;


    public Boolean logout(String token) {

        if (StringUtils.isNullOrEmpty(token)) return true;

        int uid = jwtUtils.parseUidFromToken(token);

        if (uid == -1) {
            return true;
        }

        return redisService.del("jwtCache::jwt_uid_" + uid);
    }
}
