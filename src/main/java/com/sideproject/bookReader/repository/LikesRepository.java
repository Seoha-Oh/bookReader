package com.sideproject.bookReader.repository;

import com.sideproject.bookReader.model.Likes;
import com.sideproject.bookReader.repository.custom.LikeCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>, LikeCustomRepository {
    Optional<Likes> findByFeedIdAndUserId(Long feedId, Long userId);
}
