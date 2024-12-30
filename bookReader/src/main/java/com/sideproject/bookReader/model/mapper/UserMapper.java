package com.sideproject.bookReader.model.mapper;

import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.model.dto.user.GetUserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public GetUserDto toGetUserDto(User user){
        return GetUserDto.builder()
            .name(user.getName())
            .profileImage(user.getProfileImage())
            .build();
    }
}
