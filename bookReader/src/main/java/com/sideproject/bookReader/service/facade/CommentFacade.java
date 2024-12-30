package com.sideproject.bookReader.service.facade;

import com.sideproject.bookReader.model.Comment;
import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.model.dto.comment.GetCommentDto;
import com.sideproject.bookReader.model.dto.comment.GetCommentsDto;
import com.sideproject.bookReader.model.mapper.CommentMapper;
import com.sideproject.bookReader.service.CommentService;
import com.sideproject.bookReader.service.FeedService;
import com.sideproject.bookReader.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentFacade {

    private final FeedService feedService;
    private final CommentService commentService;
    private final UserService userService;

    private final CommentMapper commentMapper;

    @Transactional
    public void writeComment(Long feedId, String content) {

        Feed feed = feedService.retrieveFeedForFacade(feedId);
        User user = userService.retrieveUserForFacade();

        commentService.createComment(feed, user, content);
    }

    public GetCommentsDto getReComments(Long parentCommentId, Pageable pageable){
        List<Comment> commentList =
            commentService.getReComments(parentCommentId, pageable);

        List<GetCommentDto> getCommentDtos = commentList.stream()
            .map(comment -> commentMapper.toGetCommentDto(comment)).toList();

        return GetCommentsDto.builder()
            .commentDtoList(getCommentDtos).build();
    }

    public GetCommentsDto getComments(Long feedId, Pageable pageable){
        Feed feed = feedService.retrieveFeedForFacade(feedId);
        List<Comment> commentList = commentService.getComments(feed, pageable);

        List<GetCommentDto> getCommentDtos = commentList.stream()
            .map(comment -> commentMapper.toGetCommentDto(comment)).toList();

        return GetCommentsDto.builder()
            .commentDtoList(getCommentDtos).build();
    }


    @Transactional
    public void writeReComment(Long parentCommentId, String content) {

        User user = userService.retrieveUserForFacade();
        Comment parent = commentService.getComment(parentCommentId);

        commentService.createReComment(parent.getFeed(), user, parent, content);
    }

    @Transactional
    public void updateComment(Long commentId, String content){
        Long userId = userService.retrieveUserIdForFacade();

        commentService.updateComment(commentId, content, userId);
    }

    @Transactional
    public void removeComment(Long commentId){
        Long userId = userService.retrieveUserIdForFacade();

        commentService.deleteComment(commentId, userId);
    }



}
