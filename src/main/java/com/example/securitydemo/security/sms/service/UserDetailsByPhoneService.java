package com.example.securitydemo.security.sms.service;

import com.example.securitydemo.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("smsLoginUserDetailsService")
@Slf4j
public class UserDetailsByPhoneService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     *
     * @param phone
     * @return 根据phone查询用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        UserDetails userDetails = userService.queryUserDetailsByPhone(phone);
        if (userDetails == null) {
            log.info("no such user");
            throw new UsernameNotFoundException("no such user");
        }
        log.info(userDetails.toString());
        return userDetails;
    }

}
