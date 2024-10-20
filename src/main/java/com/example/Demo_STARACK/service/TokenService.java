package com.example.Demo_STARACK.service;

import com.example.Demo_STARACK.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Autowired
    private JwtUtil jwtUtil; // Sử dụng JwtUtil để lấy secret và expiration

    public String generateToken(String email) {
        return jwtUtil.generateToken(email); // Sử dụng phương thức từ JwtUtil
    }

    public boolean validateToken(String token) {
        try {
            jwtUtil.extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = jwtUtil.extractAllClaims(token);
        return claims.getSubject();
    }
}
