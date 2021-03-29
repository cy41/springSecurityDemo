package com.example.securitydemo.security.entitys;

import com.example.securitydemo.mybatis.entitys.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@NoArgsConstructor
@Data
public class MyUserDetails implements UserDetails {

    private int id;
    private String name;
    private String pwd;
    private String phone;
    private List<Role> roleList;


    public MyUserDetails(int id, String name, String pwd, String phone) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    public String getPassword() {
        return pwd;
    }

    public String getUsername() {
        return id + "";
    }

    //账户没有过期

    public boolean isAccountNonExpired() {
        return true;
    }

    //用户没有锁定

    public boolean isAccountNonLocked() {
        return true;
    }

    //密码没有过期

    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return true;
    }
}
