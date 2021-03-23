package com.example.securitydemo.mybatis.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    private Long id;
    private Long uid;
    private Long rid;

    public UserRole(Long uid, Long rid) {
        this.uid = uid;
        this.rid = rid;
    }
}
