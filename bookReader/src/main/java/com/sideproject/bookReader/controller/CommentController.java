package com.sideproject.bookReader.controller;

import com.sideproject.bookReader.model.dto.comment.GetCommentsDto;
import com.sideproject.bookReader.service.facade.CommentFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentFacade commentFacade;

    @PostMapping("/{feedId}")
    public void writeComment(@PathVariable Long feedId, @RequestBody String content) {
        commentFacade.writeComment(feedId, content);
    }

    @PostMapping("/{parentCommentId}/replies")
    public void writeReComment(@PathVariable Long parentCommentId,
        @RequestBody String content) {
        commentFacade.writeReComment(parentCommentId, content);
    }

    @GetMapping("/{parentCommentId}/replies")
    public GetCommentsDto getReComments(@PathVariable Long parentCommentId,
        @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentFacade.getReComments(parentCommentId, pageable);
    }


    @GetMapping("/{feedId}")
    public GetCommentsDto getComments(@PathVariable Long feedId,
        @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentFacade.getComments(feedId, pageable);
    }

    @PutMapping("/{commentId}")
    public void updateComment(@PathVariable Long commentId,
        @RequestBody String content) {
        commentFacade.updateComment(commentId, content);
    }


    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentFacade.removeComment(commentId);
    }


}
