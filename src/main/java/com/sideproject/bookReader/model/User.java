package com.sideproject.bookReader.model;

import com.sideproject.bookReader.model.dto.OAuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID (PK)
    private String email; // 이메일 (공통 필드, 주요 식별자)

    @Column(nullable = true)
    private String name; // 사용자 이름 또는 닉네임
    @Column(nullable = true)
    private String profileImage; // 프로필 이미지 URL
    private String role; // 사용자 권한 (e.g., ROLE_USER, ROLE_ADMIN)
    @Enumerated(EnumType.STRING)
    private OAuthProvider authProvider; // OAuth 제공자 (e.g., GOOGLE, KAKAO)

    private Long providerId; // OAuth2 제공자의 고유 사용자 ID (e.g., 카카오 ID, 구글 서브 ID)



    public User updateInfo(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;

        return this;
    }
}
