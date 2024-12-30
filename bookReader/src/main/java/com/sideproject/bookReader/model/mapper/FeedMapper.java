package com.sideproject.bookReader.model.mapper;

import com.sideproject.bookReader.model.Book;
import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.model.dto.book.GetBookDto;
import com.sideproject.bookReader.model.dto.feed.CreateFeedDto;
import com.sideproject.bookReader.model.dto.feed.GetFeedDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FeedMapper {


    public Feed toEntity(CreateFeedDto createFeedDto, User user){
        return Feed.builder()
            .user(user)
            .title(createFeedDto.getTitle())
            .content(createFeedDto.getContent())
            .isSpoiler(createFeedDto.isSpoiler())
            .build();
    }

    public Feed toEntity(CreateFeedDto createFeedDto, Book book, User user){
        return Feed.builder()
            .user(user)
            .book(book)
            .title(createFeedDto.getTitle())
            .content(createFeedDto.getContent())
            .isSpoiler(createFeedDto.isSpoiler())
            .build();
    }

    public GetFeedDto toGetFeedDto(Feed feed){
        return GetFeedDto.builder()
            .feedId(feed.getId())
            .title(feed.getTitle())
            .content(feed.getContent())
            .isSpoiler(feed.isSpoiler())
            .build();
    }

}
