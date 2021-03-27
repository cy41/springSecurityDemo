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
    private int id;
    private int uid;
    private int rid;

    public UserRole(int uid, int rid) {
        this.uid = uid;
        this.rid = rid;
    }
}
