package com.sideproject.bookReader.model.mapper;

import com.sideproject.bookReader.model.Comment;
import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.model.dto.comment.GetCommentDto;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public Comment toEntity(User user, Feed feed, String content) {
        return Comment.builder()
            .user(user)
            .feed(feed)
            .content(content)
            .build();
    }

    public Comment toEntity(User user, Feed feed, Comment parent, String content) {
        return Comment.builder()
            .user(user)
            .parent(parent)
            .feed(feed)
            .content(content)
            .build();
    }

    public GetCommentDto toGetCommentDto(Comment comment) {
        return GetCommentDto.builder()
            .content(comment.getContent())
            .userDto(userMapper.toGetUserDto(comment.getUser()))
            .regDate(Timestamp.valueOf(comment.getCreatedAt()))
            .build();
    }

    public List<GetCommentDto> toGetCommentsDto(List<Comment> comments) {
        return comments.stream().map(comment -> toGetCommentDto(comment)).toList();
    }


}
