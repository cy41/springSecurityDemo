package com.example.securitydemo.mybatis.dao;

import com.example.securitydemo.mybatis.entitys.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRoleDao {

    int insert(UserRole ur);
}
