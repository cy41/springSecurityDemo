package com.example.securitydemo.mybatis.entitys;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("role")
@Data
public class Role {
    private Long id;
    private String roleName;
}
