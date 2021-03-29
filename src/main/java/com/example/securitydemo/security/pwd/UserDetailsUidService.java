package com.example.securitydemo.security.pwd;

import com.example.securitydemo.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("pwdLoginUserDetailsService")
public class UserDetailsUidService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     *
     * @param id 通过uid查找user
     * @return userDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserDetails userDetails = userService.queryUserDetailsById(id);
        if (userDetails == null) {
            throw new UsernameNotFoundException("no such user");
        }
        return userDetails;
    }

}
