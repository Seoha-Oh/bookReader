package com.sideproject.bookReader.service.facade;

import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.service.FollowService;
import com.sideproject.bookReader.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FollowFacade {
    private final FollowService followService;
    private final UserService userService;

    @Transactional
    public void follow(Long targetId){
        User target = userService.retrieveUserForFacade(targetId);
        User me= userService.retrieveUserForFacade();

        followService.follow(me, target);
    }

    @Transactional
    public void unfollow(Long targetId){
        User target = userService.retrieveUserForFacade(targetId);
        User me= userService.retrieveUserForFacade();

        followService.unfollow(me, target);
    }
}
