package com.sideproject.bookReader.model.dto.feed;

import com.querydsl.core.annotations.QueryProjection;
import com.sideproject.bookReader.model.Book;
import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.dto.book.GetBookDto;
import com.sideproject.bookReader.model.dto.comment.GetCommentDto;
import com.sideproject.bookReader.model.dto.user.GetUserDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFeedDto {
    private Long feedId;
    private String title;
    private String content;
    private boolean isSpoiler;
    private boolean isLike;
    private Long likeCnt;
    private Long commentCnt;
    private GetBookDto bookDto;
    private GetUserDto userDto;


    @QueryProjection
    public GetFeedDto(Long feedId, String title, String content, boolean isSpoiler,
        GetBookDto bookDto, boolean isLike, Long likeCnt, Long commentCnt) {
        this.feedId = feedId;
        this.title = title;
        this.content = content;
        this.isSpoiler = isSpoiler;
        this.bookDto = bookDto;
        this.isLike = isLike;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
    }
}
