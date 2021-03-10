package com.example.securitydemo.mybatis.entitys;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("user_ope_record")
@Data
public class UserOpeRecord {
    private Long id;
    private Long userId;
    private String action;
    private Date time;
}
