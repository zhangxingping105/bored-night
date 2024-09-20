package com.zxp.bored.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT工具类
 *
 * @description: JWT工具类
 * @author: zxp
 * @date: 2024/9/16 14:48
 */
@Component
public class JwtUtil {

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expiration = 1000 * 60 * 60; // 1 hour

    // 生成 token
    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration)).signWith(key).compact();
    }

    // 解析 token
    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseClaimsJws(token).getBody();
    }

    // 从 token 中提取用户名
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 检查 token 是否过期
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 验证 token
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
