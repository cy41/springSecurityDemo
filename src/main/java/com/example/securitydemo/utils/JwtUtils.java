package com.example.securitydemo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.util.Date;

public class JwtUtils {

    private static final String ID = "id";
    private static final String UID = "uid";
    private String key = "23oisdufhrl3rj;zxc/';3";

    private static JwtUtils instance = new JwtUtils();

    private JwtUtils() {

    }

    public static JwtUtils getInstance() {
        return instance;
    }

    /**
     * create token
     *
     * @param uid user id
     * @param expireMinutes 有效时间
     * @return token
     */
    public String generateToken(Long uid, int expireMinutes) {
        return Jwts.builder()
                .claim(UID, uid)
                .setExpiration(DateTime.now().plusMillis(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

    }

    // decode token
    public String parseToken(String jwt) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJwt(jwt)
                .getBody()
                .getSubject();
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
                    .parseClaimsJwt(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }

        return claims;
    }


}
