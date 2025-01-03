package com.sideproject.bookReader.config;

import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

        // 1. Authorization 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 값

            // 2. 토큰 유효성 검증
            if (jwtTokenProvider.isValidToken(token, false)) {
                // 3. 토큰에서 사용자 ID 추출
                Long userId = jwtTokenProvider.getUserIdFromToken(token, false);

                // 4. SecurityContext에 인증 정보 설정
                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                        User.builder()
                            .id(userId)
                            .build(), // 사용자 ID
                        null,
                        Collections.emptyList() // 권한
                    );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                // 토큰이 유효하지 않은 경우 SecurityContext를 초기화
                SecurityContextHolder.clearContext();
            }
        }

        // 5. 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
