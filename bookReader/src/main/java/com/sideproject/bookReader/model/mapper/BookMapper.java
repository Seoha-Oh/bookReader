package com.sideproject.bookReader.model.mapper;

import com.sideproject.bookReader.model.Book;
import com.sideproject.bookReader.model.dto.book.GetBookDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public GetBookDto toGetBookDto(Book book){
        return GetBookDto.builder()
            .isbn(book.getIsbn())
            .title(book.getTitle())
            .author(book.getAuthor())
            .imageUrl(book.getImageUrl())
            .build();
    }

}
