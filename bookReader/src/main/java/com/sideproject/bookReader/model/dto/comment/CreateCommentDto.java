package com.sideproject.bookReader.model.dto.comment;

import lombok.Builder.Default;
import lombok.Data;

@Data
public class CreateCommentDto {
    private Long feedId;
    private String content;
}
