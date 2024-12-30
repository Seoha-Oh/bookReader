package com.sideproject.bookReader.util;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * JwtAuthentication: JWT를 기반으로 인증 객체를 생성하는 클래스
 */
public class JwtAuthentication extends AbstractAuthenticationToken {

    private final Long userId; // 사용자 ID
    private final Object credentials; // 자격 증명 (JWT 토큰 또는 기타 인증 정보)

    /**
     * JwtAuthentication 생성자
     * @param userId 사용자 ID
     * @param credentials 자격 증명 (JWT 토큰)
     * @param authorities 사용자 권한
     */
    public JwtAuthentication(Long userId, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities); // 권한 설정
        this.userId = userId;
        this.credentials = credentials;
        setAuthenticated(true); // 인증 완료 상태로 설정
    }

    /**
     * 인증된 사용자의 ID 반환
     * @return 사용자 ID
     */
    @Override
    public Object getPrincipal() {
        return userId;
    }

    /**
     * 자격 증명 반환
     * @return JWT 토큰 또는 기타 인증 정보
     */
    @Override
    public Object getCredentials() {
        return credentials;
    }
}
