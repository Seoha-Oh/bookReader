package com.sideproject.bookReader.model.dto.book;

import com.querydsl.core.annotations.QueryProjection;
import com.sideproject.bookReader.model.Book;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetBookDto {
    private String title;
    private String author;
    private String imageUrl;
    private String isbn;

    @QueryProjection
    public GetBookDto(String isbn, String title, String author, String imageUrl) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
    }
}
