package com.sideproject.bookReader.controller;

import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.model.dto.TokenResponse;
import com.sideproject.bookReader.repository.UserRepository;
import com.sideproject.bookReader.util.JwtTokenProvider;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @GetMapping("/token")
    public TokenResponse generateTokens(Authentication authentication) {
        // 인증된 사용자의 정보를 가져옵니다.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = ((Map<String, String>) oAuth2User.getAttributes().get("kakao_account")).get("email");

        // 유저 데이터 조회
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // JWT 발급
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        return new TokenResponse(accessToken, refreshToken);
    }

}