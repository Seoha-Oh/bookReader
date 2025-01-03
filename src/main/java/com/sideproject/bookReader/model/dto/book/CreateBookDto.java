package com.sideproject.bookReader.model.dto.book;


import lombok.Data;

@Data
public class CreateBookDto {
    private String title;
    private String author;
    private String imageUrl;
    private String isbn;

}
