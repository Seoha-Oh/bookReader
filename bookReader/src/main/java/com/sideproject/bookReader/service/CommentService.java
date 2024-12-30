package com.sideproject.bookReader.service;


import com.sideproject.bookReader.model.Comment;
import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.repository.CommentRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void createComment(Feed feed, User user, String content) {
        commentRepository.save(
            Comment.builder()
                .feed(feed)
                .user(user)
                .content(content)
                .build()
        );
    }

    public void createReComment(Feed feed, User user, Comment parent, String content) {
        commentRepository.save(
            Comment.builder()
                .feed(feed)
                .user(user)
                .parent(parent)
                .content(content)
                .build()
        );
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException());
    }

    public List<Comment> getComments(Feed feed, Pageable pageable) {
        return commentRepository.findComments(feed.getId(), pageable);
    }

    public List<Comment> getReComments(Long parentCommentId, Pageable pageable) {
        return commentRepository.findChildComments(parentCommentId, pageable);
    }

    public void updateComment(Long commentId, String content, Long userId) {
        Comment comment = getComment(commentId);

        validate(comment, userId);

        comment.updateComment(content);
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = getComment(commentId);

        validate(comment, userId);

        commentRepository.delete(comment);
    }


    private void validate(Comment comment, Long userId) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("You do not have permission to modify this comment");
        }
    }
}
