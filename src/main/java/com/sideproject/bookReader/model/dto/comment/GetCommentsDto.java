package com.sideproject.bookReader.model.dto.comment;

import com.sideproject.bookReader.service.LikesService;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCommentsDto {
    private List<GetCommentDto> commentDtoList;
}
