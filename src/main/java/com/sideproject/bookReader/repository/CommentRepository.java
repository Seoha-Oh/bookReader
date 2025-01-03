package com.sideproject.bookReader.repository;

import com.sideproject.bookReader.model.Comment;
import com.sideproject.bookReader.repository.custom.CommentCustomRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> , CommentCustomRepository {

}
