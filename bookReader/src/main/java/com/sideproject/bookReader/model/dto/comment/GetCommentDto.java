package com.sideproject.bookReader.model.dto.comment;

import com.sideproject.bookReader.model.dto.user.GetUserDto;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCommentDto {
    private String content;
    private Timestamp regDate;
    private GetUserDto userDto;
}
