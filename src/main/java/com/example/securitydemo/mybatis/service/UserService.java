package com.example.securitydemo.mybatis.service;

import com.example.securitydemo.mybatis.dao.UserDao;
import com.example.securitydemo.mybatis.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao = null;


    public UserDetails queryUserDetailsById(String id) {
        try {
            Long uId = Long.valueOf(id);
            return userDao.queryUserDetailsById(uId);
        } catch (NumberFormatException exception) {
            return null;
        }

    }

    public UserDetails queryUserDetailsByPhone(String phone) {
        return userDao.queryUserDetailsByPhone(phone);
    }

    public Long queryUidByPhone(String phone) {
        return userDao.queryIdByPhone(phone);
    }

}
