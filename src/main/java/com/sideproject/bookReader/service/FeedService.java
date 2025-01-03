package com.sideproject.bookReader.service;

import com.sideproject.bookReader.model.Book;
import com.sideproject.bookReader.model.Comment;
import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.model.dto.comment.GetCommentDto;
import com.sideproject.bookReader.model.dto.feed.CreateFeedDto;
import com.sideproject.bookReader.model.dto.feed.GetFeedDto;
import com.sideproject.bookReader.model.dto.feed.GetFeedListDto;
import com.sideproject.bookReader.model.dto.feed.UpdateFeedDto;
import com.sideproject.bookReader.model.dto.user.GetUserDto;
import com.sideproject.bookReader.model.mapper.BookMapper;
import com.sideproject.bookReader.model.mapper.CommentMapper;
import com.sideproject.bookReader.model.mapper.FeedMapper;
import com.sideproject.bookReader.model.mapper.UserMapper;
import com.sideproject.bookReader.repository.FeedRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    private final FeedMapper feedMapper;

    public void createFeed(CreateFeedDto createFeedDto, User user, Book book){
        Feed feed = feedMapper.toEntity(createFeedDto, book, user);
        feedRepository.save(feed);
    }

    public void deleteFeed(Feed feed) {
        feedRepository.delete(feed);
    }


    public void updateFeed(Feed feed, Book book, UpdateFeedDto updateFeedDto) {

        feed.updateFeed(book, updateFeedDto.getTitle(), updateFeedDto.getContent(),
            updateFeedDto.isSpoiler());
    }

    public GetFeedListDto getFeedList(Long userId, Pageable pageable) {

        Page<GetFeedDto> feedPages = feedRepository.findFeedsByFollowing(userId, pageable);

        return new GetFeedListDto(
            feedPages.getContent(),
            feedPages.getTotalElements(),
            feedPages.getTotalPages(),
            feedPages.getNumber()
        );
    }

    public Feed retrieveFeedForFacade(Long feedId) {
        return feedRepository.findFetchJoin(feedId).orElseThrow(() -> new RuntimeException());
    }



}
