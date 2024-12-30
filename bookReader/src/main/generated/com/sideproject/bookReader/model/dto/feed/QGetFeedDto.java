package com.sideproject.bookReader.model.dto.feed;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.sideproject.bookReader.model.dto.feed.QGetFeedDto is a Querydsl Projection type for GetFeedDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGetFeedDto extends ConstructorExpression<GetFeedDto> {

    private static final long serialVersionUID = 9630782L;

    public QGetFeedDto(com.querydsl.core.types.Expression<Long> feedId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Boolean> isSpoiler, com.querydsl.core.types.Expression<? extends com.sideproject.bookReader.model.dto.book.GetBookDto> bookDto, com.querydsl.core.types.Expression<Boolean> isLike, com.querydsl.core.types.Expression<Long> likeCnt, com.querydsl.core.types.Expression<Long> commentCnt) {
        super(GetFeedDto.class, new Class<?>[]{long.class, String.class, String.class, boolean.class, com.sideproject.bookReader.model.dto.book.GetBookDto.class, boolean.class, long.class, long.class}, feedId, title, content, isSpoiler, bookDto, isLike, likeCnt, commentCnt);
    }

}

