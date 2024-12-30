package com.sideproject.bookReader.repository.custom;

import com.sideproject.bookReader.model.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {
    List<Comment> findChildComments(Long parentCommentId, Pageable pageable);
    List<Comment> findComments(Long feedId, Pageable pageable);

}
