package com.example.securitydemo.security.entitys;

import com.example.securitydemo.mybatis.entitys.Role;
import com.example.securitydemo.mybatis.entitys.User;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class MyUserDetails implements UserDetails {

    private Long id;
    private String name;
    private String pwd;
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private List<Role> roleList;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }


    public String getPassword() {
        return pwd;
    }


    public String getUsername() {
        return name;
    }

    //账户过期否

    public boolean isAccountNonExpired() {
        return true;
    }

    //用户锁定否

    public boolean isAccountNonLocked() {
        return true;
    }

    //密码过期否

    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return true;
    }
}
