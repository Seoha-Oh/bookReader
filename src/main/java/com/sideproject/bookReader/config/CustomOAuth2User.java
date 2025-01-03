package com.sideproject.bookReader.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {
    private final Map<String, Object> attributes;
    private final String accessToken;
    private final String refreshToken;
    private final String role;

    public CustomOAuth2User(Map<String, Object> attributes, String accessToken, String refreshToken, String role) {
        this.attributes = attributes;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role); // 사용자의 권한 반환
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
