package com.sideproject.bookReader.service.facade;

import com.sideproject.bookReader.model.Book;
import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.model.dto.book.GetBookDto;
import com.sideproject.bookReader.model.dto.feed.CreateFeedDto;
import com.sideproject.bookReader.model.dto.feed.GetFeedDto;
import com.sideproject.bookReader.model.dto.feed.GetFeedListDto;
import com.sideproject.bookReader.model.dto.feed.UpdateFeedDto;
import com.sideproject.bookReader.model.dto.user.GetUserDto;
import com.sideproject.bookReader.model.mapper.BookMapper;
import com.sideproject.bookReader.model.mapper.FeedMapper;
import com.sideproject.bookReader.model.mapper.UserMapper;
import com.sideproject.bookReader.service.BookService;
import com.sideproject.bookReader.service.FeedService;
import com.sideproject.bookReader.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedFacade {

    private final FeedService feedService;
    private final BookService bookService;
    private final UserService userService;

    private final FeedMapper feedMapper;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;


    @Transactional
    public void writeFeed(CreateFeedDto createFeedDto) {
        User user = userService.retrieveUserForFacade();

        Book book;
        if (!bookService.existBook(createFeedDto.getCreateBookDto().getIsbn())) {
            book = bookService.saveBook(createFeedDto.getCreateBookDto());
        } else {
            book = bookService.retrieveBookForService(createFeedDto.getCreateBookDto().getIsbn());
        }

        feedService.createFeed(createFeedDto, user, book);
    }

    public GetFeedDto getFeedDetail(Long feedId) {
        Feed feed = feedService.retrieveFeedForFacade(feedId);

        GetFeedDto getFeedDto = feedMapper.toGetFeedDto(feed);
        GetUserDto getUserDto = userMapper.toGetUserDto(feed.getUser());
        GetBookDto getBookDto = bookMapper.toGetBookDto(feed.getBook());

        getFeedDto.setUserDto(getUserDto);
        getFeedDto.setBookDto(getBookDto);

        return getFeedDto;
    }

    public GetFeedListDto getFeedList(Pageable pageable) {
        Long userId = userService.retrieveUserIdForFacade();

        return feedService.getFeedList(userId, pageable);
    }

    @Transactional
    public void updateFeed(Long feedId, UpdateFeedDto updateFeedDto) {
        Feed feed = feedService.retrieveFeedForFacade(feedId);
        User user = userService.retrieveUserForFacade();

        validate(feed, user.getId());

        Book book = null;
        if (!bookService.existBook(updateFeedDto.getCreateBookDto().getIsbn())) {
            book = bookService.saveBook(updateFeedDto.getCreateBookDto());
        } else {
            book = bookService.retrieveBookForService(updateFeedDto.getCreateBookDto().getIsbn());
        }

        feedService.updateFeed(feed, book, updateFeedDto);

    }

    @Transactional
    public void removeFeed(Long feedId) {
        Feed feed = feedService.retrieveFeedForFacade(feedId);
        Long userId = userService.retrieveUserIdForFacade();

        validate(feed, userId);

        feedService.deleteFeed(feed);
    }


    private void validate(Feed feed, Long loginId) {

        if (!feed.getUser().getId().equals(loginId)) {
            throw new RuntimeException("You do not have permission to modify this Feed");
        }
    }

}
