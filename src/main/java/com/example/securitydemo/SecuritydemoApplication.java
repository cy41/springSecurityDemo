package com.example.securitydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableCaching
@MapperScan("com.example.securitydemo.mybatis.dao")
public class SecuritydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuritydemoApplication.class, args);
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    private void initRedisTemplate() {
        RedisSerializer redisSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setKeySerializer(redisSerializer);
    }
}
