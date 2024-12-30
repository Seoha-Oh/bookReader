package com.sideproject.bookReader.model.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserDto {
    private String name;
    private String profileImage;
}
