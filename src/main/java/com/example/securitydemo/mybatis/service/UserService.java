package com.example.securitydemo.mybatis.service;

import com.example.securitydemo.mybatis.dao.UserDao;
import com.example.securitydemo.mybatis.dao.UserRoleDao;
import com.example.securitydemo.mybatis.entitys.User;
import com.example.securitydemo.mybatis.entitys.UserRole;
import com.example.securitydemo.security.entitys.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserDao userDao = null;

    @Autowired
    private UserRoleDao userRoleDao = null;


    @Cacheable(value = "userCache", key = "'user_id_' + #id", unless = "#result != 'null'")
    @Transactional
    public MyUserDetails queryUserDetailsById(String id) {
        try {
            int uId = Integer.parseInt(id);
            return userDao.queryUserDetailsById(uId);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    @Cacheable(value = "userCache", key = "'user_id_' + #id", unless = "#result != 'null'")
    @Transactional
    public MyUserDetails queryUserDetailsById(int id) {
        return userDao.queryUserDetailsById(id);
    }


    /**
     * 注册user，同时注册userRole角色为普通
     * @param user
     * @return
     */
    @Transactional
    @CachePut(value = "redisCache", key = "'user_' + #result.id")
    public User insertUser(User user) {
        userDao.insertUser(user);
        userRoleDao.insert(new UserRole(user.getId(), 1));
        return user;
    }

    /**
     * 1。redis
     * 2。sql查询，没有则为注册转3
     * 3。注册
     * @param phone
     * @return userDetails
     */
    @Transactional
    @Cacheable(value = "userCache", key = "'phone_' + #phone")
    public MyUserDetails queryUserDetailsByPhone(String phone) {
        MyUserDetails userDetails = userDao.queryUserDetailsByPhone(phone);
        log.debug("userDetails {}", userDetails);
        if (userDetails != null) {
            return userDetails;
        }
        User user = new User();
        user.setPhone(phone);
        insertUser(user);
        return userDao.queryUserDetailsByPhone(phone);
    }

    @Transactional
    @Cacheable(value = "phoneUid", key = "'user_phone_' + #phone", unless = "#result != 'null'")
    public Integer queryUidByPhone(String phone) {
        return userDao.queryIdByPhone(phone);
    }

}
