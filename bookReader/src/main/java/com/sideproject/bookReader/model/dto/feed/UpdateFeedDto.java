package com.sideproject.bookReader.model.dto.feed;

import com.sideproject.bookReader.model.dto.book.CreateBookDto;
import com.sideproject.bookReader.model.dto.book.GetBookDto;
import lombok.Data;

@Data
public class UpdateFeedDto {
    private CreateBookDto createBookDto;
    private String title;
    private String content;
    private boolean isSpoiler;

}
