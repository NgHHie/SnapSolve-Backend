package com.example.snapsolve.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.snapsolve.models.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 giờ
    
    @Value("${app.jwt.secret}")
    private String secretKeyString;
    
    // Tạo SecretKey an toàn từ chuỗi cấu hình
    private SecretKey getSigningKey() {
        // Tạo khóa đủ an toàn cho HS512
        return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
                .setIssuer("SnapSolve")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            // Token đã hết hạn
            return false;
        } catch (IllegalArgumentException ex) {
            // Token trống hoặc null
            return false;
        } catch (MalformedJwtException ex) {
            // Token không đúng định dạng
            return false;
        } catch (UnsupportedJwtException ex) {
            // Token không được hỗ trợ
            return false;
        } catch (SignatureException ex) {
            // Xác thực chữ ký thất bại
            return false;
        }
    }
    
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
    
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}