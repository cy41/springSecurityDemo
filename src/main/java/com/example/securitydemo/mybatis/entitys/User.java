package com.example.securitydemo.mybatis.entitys;

import com.example.securitydemo.security.entitys.MyUserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("user")
@Data
@NoArgsConstructor
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

    public MyUserDetails typeToUserDetails() {
        return new MyUserDetails(id, name, pwd, phone);
    }
}
