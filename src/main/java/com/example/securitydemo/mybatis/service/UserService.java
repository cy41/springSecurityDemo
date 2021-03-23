package com.example.securitydemo.mybatis.service;

import com.example.securitydemo.mybatis.dao.UserDao;
import com.example.securitydemo.mybatis.dao.UserRoleDao;
import com.example.securitydemo.mybatis.entitys.User;
import com.example.securitydemo.mybatis.entitys.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao = null;

    @Autowired
    private UserRoleDao userRoleDao = null;


    @Transactional
    public UserDetails queryUserDetailsById(String id) {
        try {
            Long uId = Long.valueOf(id);
            return userDao.queryUserDetailsById(uId);
        } catch (NumberFormatException exception) {
            return null;
        }
    }


    @Transactional
    @CachePut(value = "redisCache", key = "'user_' + #result.id")
    public User insertUser(User user) {
        userDao.insertUser(user);
        userRoleDao.insert(new UserRole(user.getId(), 1L));
        return user;
    }


    @Transactional
    public UserDetails queryUserDetailsByPhone(String phone) {
        UserDetails userDetails = userDao.queryUserDetailsByPhone(phone);
        if (userDetails != null) {
            return userDetails;
        }
        User user = new User();
        user.setPhone(phone);
        insertUser(user);
        return userDao.queryUserDetailsByPhone(phone);
    }

    @Transactional
    @Cacheable(value = "phoneUid", key = "'user_phone_' + #phone")
    public Long queryUidByPhone(String phone) {
        return userDao.queryIdByPhone(phone);
    }

}
