package com.example.securitydemo.mybatis.dao;

import com.example.securitydemo.mybatis.entitys.User;
import com.example.securitydemo.security.entitys.MyUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    User queryUserById(Long id);

    MyUserDetails queryUserDetailsById(Long id);

    MyUserDetails queryUserDetailsByPhone(String phone);
}
