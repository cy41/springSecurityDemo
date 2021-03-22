package com.example.securitydemo.security.config;

import com.example.securitydemo.security.jwt.config.JwtLoginAuthenticationConfiguration;
import com.example.securitydemo.security.sms.config.SmsLoginAuthConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class MyWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SmsLoginAuthConfiguration smsLoginAuthConfiguration;

    @Autowired
    private JwtLoginAuthenticationConfiguration jwtLoginAuthenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 注入authenticationManager对象
    @Bean

    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //设定访问权限

    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .disable()
                .apply(smsLoginAuthConfiguration)
                .and()
                .apply(jwtLoginAuthenticationConfiguration)
                .and()
                // 设置URL的授权
                .authorizeRequests()
                // 这里需要将登录页面放行
                .antMatchers("/login","/sms/*","/failure","/jwtLogin", "/insertUser", "/testPwd")
                .permitAll()
                // anyRequest() 所有请求   authenticated() 必须被认证
                .anyRequest().authenticated()
                .and()
                // 关闭csrf
                .csrf().disable();

        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
