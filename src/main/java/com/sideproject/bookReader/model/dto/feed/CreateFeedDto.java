package com.sideproject.bookReader.model.dto.feed;

import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.dto.book.CreateBookDto;
import lombok.Data;

@Data
public class CreateFeedDto {
    private CreateBookDto createBookDto;
    private String title;
    private String content;
    private boolean isSpoiler;

}
