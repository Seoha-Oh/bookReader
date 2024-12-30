package com.sideproject.bookReader.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

@AllArgsConstructor
public class SecurityUtils {

    /**
     * SecurityContext에서 사용자 ID를 추출하는 메서드
     *
     * @return 사용자 ID 또는 null (인증되지 않은 경우)
     */
    public static String getUserIdFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            System.out.println("Authentication is null in SecurityContext");
            return null;
        }

        if (authentication instanceof JwtAuthentication) {
            Object principal = authentication.getPrincipal();
            System.out.println("JwtAuthentication Principal (User ID): " + principal);
            return principal != null ? principal.toString() : null;
        }

        System.out.println("No valid JwtAuthentication found in SecurityContext");
        return null;
    }
}
