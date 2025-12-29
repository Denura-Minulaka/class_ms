package com.class_ms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    private final Key key;

    public JwtUtil() {
        String SECRET = "7d9F!2xA@9#LkP0mQwZ8sR1EJfYtU4cB";
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public Integer extractTeacherId(String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Integer.valueOf(claims.getSubject());
        } catch (Exception e) {
            System.out.println("JWT parsing failed: " + e.getMessage());
            throw e;
        }
    }

    public Integer extractStudentId(String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Integer.valueOf(claims.getSubject());
        } catch (Exception e) {
            System.out.println("JWT parsing failed: " + e.getMessage());
            throw e;
        }
    }
}
