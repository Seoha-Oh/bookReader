package com.sideproject.bookReader.service;

import com.sideproject.bookReader.model.RefreshToken;
import com.sideproject.bookReader.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // Refresh Token 저장 또는 업데이트
    @Transactional
    public void saveRefreshToken(Long userId, String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByUserId(userId);

        if (token == null) {
            // 새로 저장
            token = RefreshToken.builder().userId(userId).refreshToken(refreshToken).build();
        } else {
            // 기존 토큰 업데이트
            token.updateRefreshToken(refreshToken);
        }

        refreshTokenRepository.save(token);
    }

    // Refresh Token 검색
    public String getRefreshToken(Long userId) {
        RefreshToken token = refreshTokenRepository.findByUserId(userId);
        return token != null ? token.getRefreshToken() : null;
    }
}