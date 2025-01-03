package com.sideproject.bookReader.controller;

import com.sideproject.bookReader.service.facade.FollowFacade;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@AllArgsConstructor
public class FollowController {
    private final FollowFacade followFacade;

    @PostMapping("/{targetId}")
    public void follow(@PathVariable Long targetId) {
        followFacade.follow(targetId);
    }

    @DeleteMapping("/{targetId}")
    public void unfollow(@PathVariable Long targetId) {
        followFacade.unfollow(targetId);
    }
}
