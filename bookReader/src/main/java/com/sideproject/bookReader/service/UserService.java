package com.sideproject.bookReader.service;

import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User retrieveUserForFacade(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public User retrieveUserForFacade(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException());
    }

    public Long retrieveUserIdForFacade(){
        // SecurityContext에서 인증 정보 추출
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}
