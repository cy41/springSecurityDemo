package com.example.securitydemo.mybatis.entitys;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("role")
@Data
public class Role {
    private int id;
    private String roleName;
}
