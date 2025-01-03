package com.sideproject.bookReader.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private static final String SECRET_KEY = "your-secret-key"; // 액세스 토큰 서명 키
    private static final String REFRESH_SECRET_KEY = "your-refresh-secret-key"; // 리프레시 토큰 서명 키
    private static final long ACCESS_EXPIRATION_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7; // 7일

    /**
     * 액세스 토큰 생성
     */
    public String generateAccessToken(Long userId) {
        return JWT.create()
            .withSubject(String.valueOf(userId)) // 사용자 ID를 Subject로 추가
            .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME)) // 만료 시간
            .sign(Algorithm.HMAC256(SECRET_KEY)); // 서명
    }

    /**
     * 리프레시 토큰 생성
     */
    public String generateRefreshToken(Long userId) {
        return JWT.create()
            .withSubject(String.valueOf(userId)) // 사용자 ID를 Subject로 추가
            .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME)) // 만료 시간
            .sign(Algorithm.HMAC256(REFRESH_SECRET_KEY)); // 서명
    }

    /**
     * JWT에서 사용자 ID 추출
     */
    public Long getUserIdFromToken(String token, boolean isRefresh) {
        try {
            String secret = isRefresh ? REFRESH_SECRET_KEY : SECRET_KEY;
            return Long.valueOf(JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getSubject()); // Subject에서 사용자 ID 추출
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired JWT token", e);
        }
    }

    /**
     * JWT 유효성 확인
     */
    public boolean isValidToken(String token, boolean isRefresh) {
        try {
            String secret = isRefresh ? REFRESH_SECRET_KEY : SECRET_KEY;
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (TokenExpiredException e) {
            System.out.println("Token has expired: " + e.getMessage());
            return false;
        } catch (JWTVerificationException e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }
}