package com.example.securitydemo.mybatis.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("user")
@Data
public class User {
    private Long id;
    private String name;
    private String pwd;
    private String phone;

    public User(String name, String pwd, String phone) {
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
    }
}
