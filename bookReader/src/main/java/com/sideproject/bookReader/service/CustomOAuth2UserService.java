package com.sideproject.bookReader.service;

import com.sideproject.bookReader.config.CustomOAuth2User;
import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.repository.UserRepository;
import com.sideproject.bookReader.util.JwtTokenProvider;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 카카오에서 받은 정보 매핑
        String provider = "kakao";
        Long providerId = (Long) oAuth2User.getAttributes().get("id");
        String email = ((Map<String, String>) oAuth2User.getAttributes().get("kakao_account")).get("email");
        String name = ((Map<String, String>) oAuth2User.getAttributes().get("properties")).get("nickname");

        Optional<User> userOptional = userRepository.findByEmail(email);

        userOptional.orElseGet(() -> userRepository.save(
            User.builder()
                .email(email)
                .providerId(providerId)
                .name(name)
                .build()));

        return oAuth2User;
    }

}