package com.sideproject.bookReader.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideproject.bookReader.model.Comment;
import com.sideproject.bookReader.model.QComment;

import com.sideproject.bookReader.model.QUser;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Comment> findChildComments(Long parentCommentId, Pageable pageable) {
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        return jpaQueryFactory.selectFrom(comment)
            .innerJoin(comment.user, user).fetchJoin()
            .where(comment.parent.id.eq(parentCommentId))
            .orderBy(comment.createdAt.desc()) //
            .offset(pageable.getOffset()) // 페이징 처리 시작 위치
            .limit(pageable.getPageSize()) // 한 페이지 크기
            .fetch();
    }

    @Override
    public List<Comment> findComments(Long feedId, Pageable pageable) {
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        return jpaQueryFactory.selectFrom(comment)
            .innerJoin(comment.user, user).fetchJoin()
            .where(comment.feed.id.eq(feedId))
            .orderBy(comment.createdAt.desc()) //
            .offset(pageable.getOffset()) // 페이징 처리 시작 위치
            .limit(pageable.getPageSize()) // 한 페이지 크기
            .fetch();
    }
}
