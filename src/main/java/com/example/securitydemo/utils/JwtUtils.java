package com.example.securitydemo.utils;

import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtils {

    private static final String UID = "uid";
    private static final String key = "234985798alfewqrl'[]1fsdf;4";

    /**
     * create token
     *
     * @param uid user id
     * @param expireDate 有效时间
     * @return token
     */
    @CachePut(value = "jwtCache", key = "'jwt_uid_' + #uid")
    public String generateToken(int uid, Date expireDate) {
        System.out.println("now " + DateTime.now());
        return Jwts.builder()
                .claim(UID, uid)
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setIssuedAt(DateTime.now().toDate())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

    }

    // decode token
    public String parseToken(String jwt) {
        Claims claims = getClaimsFromToken(jwt);
        if (claims == null) {
            return "";
        }
        return claims.getSubject();
    }

    public int parseUidFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return -1;
        }
        return Integer.parseInt(claims.get("uid").toString());
    }

    // verify token
    public boolean isTokenValid(String jwt) {
        try {
            parseToken(jwt);
        } catch (Throwable throwable) {
            return false;
        }

        return true;
    }

    // 返回过期时间戳
    public Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        } else {
            return claims.getExpiration();
        }
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        System.out.println("claims " + claims);

        return claims;
    }


}
