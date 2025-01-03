package com.sideproject.bookReader.model.dto.book;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sideproject.bookReader.model.dto.book.QGetBookDto is a Querydsl Projection type for GetBookDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetBookDto extends ConstructorExpression<GetBookDto> {

    private static final long serialVersionUID = 818975976L;

    public QGetBookDto(com.querydsl.core.types.Expression<String> isbn, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> author, com.querydsl.core.types.Expression<String> imageUrl) {
        super(GetBookDto.class, new Class<?>[]{String.class, String.class, String.class, String.class}, isbn, title, author, imageUrl);
    }

}

