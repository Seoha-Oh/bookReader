package com.sideproject.bookReader.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RefreshToken {
    @Id
    private Long userId; // 사용자 ID (Primary Key)

    @OneToOne(fetch = FetchType.LAZY) // 지연 로딩으로 User 연관 설정
    @JoinColumn(name = "user_id") // 외래 키 매핑
    private User user;

    @Column(nullable = false)
    private String refreshToken; // Refresh Token

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

}