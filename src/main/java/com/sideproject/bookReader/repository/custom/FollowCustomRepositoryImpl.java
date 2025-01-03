package com.sideproject.bookReader.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideproject.bookReader.model.QFollow;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FollowCustomRepositoryImpl implements FollowCustomRepository {

    private final JPAQueryFactory queryFactory;

}
