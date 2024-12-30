package com.sideproject.bookReader.repository.custom;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.QBook;
import com.sideproject.bookReader.model.QComment;
import com.sideproject.bookReader.model.QFeed;
import com.sideproject.bookReader.model.QFollow;
import com.sideproject.bookReader.model.QLikes;
import com.sideproject.bookReader.model.QUser;
import com.sideproject.bookReader.model.dto.book.QGetBookDto;
import com.sideproject.bookReader.model.dto.feed.GetFeedDto;
import com.sideproject.bookReader.model.dto.feed.QGetFeedDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


@RequiredArgsConstructor
public class FeedCustomRepositoryImpl implements FeedCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public GetFeedDto findFeedDtoFetchJoin(Long feedId) {
        QFeed feed = QFeed.feed;
        QBook book = QBook.book;
        QUser user = QUser.user;

        return jpaQueryFactory
            .select(new QGetFeedDto(
                feed.id,
                feed.title,
                feed.content,
                feed.isSpoiler,
                new QGetBookDto(feed.book.isbn,
                    feed.book.title,
                    feed.book.author,
                    feed.book.imageUrl),
                Expressions.constant(false),
                Expressions.constant(0L),
                Expressions.constant(0L))
            ).from(feed)
            .innerJoin(feed.user, user).fetchJoin()
            .innerJoin(feed.book, book).fetchJoin()
            .where(feed.id.eq(feedId))
            .fetchOne();
    }

    @Override
    public List<GetFeedDto> findFeedDtosFetchJoin() {
        return null;
    }

    @Override
    public Page<GetFeedDto> findFeedsByFollowing(Long userId, Pageable pageable) {

        List<GetFeedDto> feedDtoList = findFeedFetchWithUserAndBook(userId, pageable);

        List<Long> feedIds = feedDtoList.stream()
            .map(GetFeedDto::getFeedId).toList();

        Map<Long, Long> likeCounts = fetchLikeCount(feedIds);
        Map<Long, Long> commentCounts = fetchCommentCounts(feedIds);
        Map<Long, Boolean> isLikes = fetchIsLike(feedIds, userId);

        feedDtoList.forEach(
            getFeedDto -> {
                Long feedId = getFeedDto.getFeedId();
                getFeedDto.setLikeCnt(likeCounts.get(feedId));
                getFeedDto.setCommentCnt(commentCounts.get(feedId));
                getFeedDto.setLike(isLikes.get(feedId));
            }
        );

        long totalElements = jpaQueryFactory
            .select(QFeed.feed.count())
            .from(QFeed.feed)
            .where(QFeed.feed.user.id.in(getFollowedUserIds(userId)))
            .fetchOne();

        return new PageImpl<>(feedDtoList, pageable, totalElements);
    }

    @Override
    public Optional<Feed> findFetchJoin(Long feedId) {
        QFeed feed = QFeed.feed;
        QBook book = QBook.book;
        QUser user = QUser.user;

        return Optional.ofNullable(jpaQueryFactory
            .selectFrom(feed)
            .innerJoin(feed.user, user).fetchJoin()
            .innerJoin(feed.book, book).fetchJoin()
            .where(feed.id.eq(feedId))
            .fetchOne());
    }

    private List<GetFeedDto> findFeedFetchWithUserAndBook(Long currentUserId, Pageable pageable) {
        QFeed feed = QFeed.feed;
        QBook book = QBook.book;
        QUser user = QUser.user;

        return jpaQueryFactory
            .select(new QGetFeedDto(
                feed.id,
                feed.title,
                feed.content,
                feed.isSpoiler,
                new QGetBookDto(book.isbn,
                    book.title,
                    book.author,
                    book.imageUrl),
                    Expressions.constant(false),
                Expressions.constant(0L),
                Expressions.constant(0L))
            ).from(feed)
            .innerJoin(feed.user, user)
            .innerJoin(feed.book, book)
            .where(feed.user.id.in(getFollowedUserIds(currentUserId))
            ).orderBy(feed.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    }

    private Map<Long, Boolean> fetchIsLike(List<Long> feedIds, Long loginId){
        QLikes likes = QLikes.likes;
        QFeed feed = QFeed.feed;

        return jpaQueryFactory
                .select(feed.id,
                        new CaseBuilder()
                                .when(likes.user.id.eq(loginId)).then(true) // 좋아요 했으면 true
                                .otherwise(false)) // 아니면 false
                .from(feed)
                .leftJoin(likes).on(likes.feed.eq(feed).and(likes.user.id.eq(loginId))) // 좋아요 여부를 LEFT JOIN으로 확인
                .where(feed.id.in(feedIds))
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(feed.id), // feed ID를 key로 사용
                        tuple -> tuple.get(1, Boolean.class) // 좋아요 여부를 value로 매핑
                ));
    }

    // 좋아요 개수를 가져오는 메서드
    private Map<Long, Long> fetchLikeCount(List<Long> feedIds) {
        QLikes likes = QLikes.likes;
        QFeed feed = QFeed.feed;

        return jpaQueryFactory
            .select(feed.id, likes.id.count())
            .from(likes)
            .innerJoin(likes.feed, feed)
            .where(feed.id.in(feedIds))
            .groupBy(feed.id).fetch()
            .stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(feed.id),
                tuple -> tuple.get(likes.id.count())
            ));
    }

    // 댓글 개수를 가져오는 메서드

    /*
    *
    * fetchCommentCounts를 유지해야 하는 경우
댓글의 개수만 필요하고, 불필요한 데이터 로드를 방지하고 싶을 때.
feed와 comment 관계가 1:N이며, N+1 문제가 우려되는 경우.
groupBy로 댓글 개수를 한 번에 가져오는 방식이 더 효율적일 때.
fetchCommentCounts가 불필요한 경우
댓글 데이터를 모두 가져와야 하고, default_batch_fetch_size로도 충분히 최적화가 가능할 때.
전체 데이터를 한 번에 로드해도 성능에 큰 문제가 없을 때.
    * */
    private Map<Long, Long> fetchCommentCounts(List<Long> feedIds) {
        QComment comment = QComment.comment;
        QFeed feed = QFeed.feed;

        return jpaQueryFactory
            .select(feed.id, comment.id.count())
            .from(comment)
            .innerJoin(comment.feed, feed)
            .where(feed.id.in(feedIds))
            .groupBy(feed.id).fetch()
            .stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(feed.id),
                tuple -> tuple.get(comment.id.count())
            ));
    }

    private JPAQuery<Long> getFollowedUserIds(Long currentUserId) {
        QFollow follow = QFollow.follow;

        return jpaQueryFactory.select(follow.target.id)
            .from(follow)
            .where(follow.source.id.eq(currentUserId));
    }
}
