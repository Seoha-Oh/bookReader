package com.sideproject.bookReader.repository.custom;

public interface LikeCustomRepository {
    boolean isLike(Long userId, Long feedId);

}
