package com.example.securitydemo.security.jwt.service;

import com.example.securitydemo.mybatis.service.UserService;
import com.example.securitydemo.security.entitys.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("jwtLoginUserDetailsService")
public class JwtLoginUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserDetails userDetails = userService.queryUserDetailsById(id);
        if (userDetails == null) {
            throw new UsernameNotFoundException("no such user");
        }
        return userDetails;
    }


    public String getToken(MyUserDetails userDetails) {
        return "asdfasdfasdf";
    }

    public MyUserDetails getUserFromToken(String token) {
        return null;
    }
}
