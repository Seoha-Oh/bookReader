package com.sideproject.bookReader.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideproject.bookReader.model.QLikes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LikeCustomRepositoryImpl implements LikeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isLike(Long userId, Long feedId) {
        QLikes likes = QLikes.likes;
        return queryFactory.selectOne()
            .from(likes)
            .where(
                likes.user.id.eq(userId),
                likes.feed.id.eq(feedId)
            ).fetchFirst() != null;
    }
}
