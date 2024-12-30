package com.sideproject.bookReader.controller;

import com.sideproject.bookReader.service.LikesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@AllArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/{feedId}")
    public void addLike(@PathVariable Long feedId){
        likesService.addLikes(feedId);
    }

    @DeleteMapping("/{feedId}")
    public void removeLike(@PathVariable Long feedId){
        likesService.deleteLikes(feedId);
    }
}
