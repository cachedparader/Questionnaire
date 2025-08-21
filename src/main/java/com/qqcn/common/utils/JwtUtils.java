package com.qqcn.common.utils;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtUtils {


    @Value("${wjj.jwt.expire}")
    private long expire;

    @Value("${wjj.jwt.secret}")
    private String secret;

    // 创建jwt
    public String createJwt(Object data){
        long currentTime = System.currentTimeMillis();
        long expireTime = currentTime + (expire * 60 * 1000);
        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ","jwt")
                .setHeaderParam("alg", SignatureAlgorithm.HS256)
                .setId(UUID.randomUUID().toString())
                .setIssuer("system")
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expireTime))
                .setSubject(JSON.toJSONString(data))
                .signWith(encodeSecret(secret),SignatureAlgorithm.HS256);
        return builder.compact();
    }

    private Key encodeSecret(String secret){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }



    // 解析
    public boolean validJwt(String jwt){
        try {
            jwt = jwt.replace("Bearer ", "");
            Jwts.parserBuilder().setSigningKey(encodeSecret(secret))
                    .build().parseClaimsJws(jwt);
        } catch (Exception e) {
            log.error(e.getMessage());
            return  false;
        }
        return true;
    }

    public <T> T parsseJwt(String jwt, Class<T> clazz){
        try {
            jwt = jwt.replace("Bearer ", "");
            String subject = Jwts.parserBuilder().setSigningKey(encodeSecret(secret))
                    .build().parseClaimsJws(jwt).getBody().getSubject();
            return JSON.parseObject(subject,clazz) ;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


}
