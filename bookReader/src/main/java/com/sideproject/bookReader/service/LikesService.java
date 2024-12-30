package com.sideproject.bookReader.service;

import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.Likes;
import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;

    private final UserService userService;
    private final FeedService feedService;


    public void deleteLikes(Long feedId){
        if(isLikedFeed(feedId)){
            User user = userService.retrieveUserForFacade();
            Feed feed = feedService.retrieveFeedForFacade(feedId);

            likesRepository.delete(Likes.builder()
                .feed(feed)
                .user(user)
                .build());
        }

    }

    public void addLikes(Long feedId){
        if(!isLikedFeed(feedId)){
            User user = userService.retrieveUserForFacade();
            Feed feed = feedService.retrieveFeedForFacade(feedId);

            likesRepository.save(Likes.builder()
                    .feed(feed)
                    .user(user)
                .build());
        }
    }

    public boolean isLikedFeed(Long feedId){
        Long userId = userService.retrieveUserIdForFacade();
        return likesRepository.isLike(userId, feedId);
    }
}
