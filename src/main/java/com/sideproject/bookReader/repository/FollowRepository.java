package com.sideproject.bookReader.repository;

import com.sideproject.bookReader.model.Follow;
import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.repository.custom.FollowCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowCustomRepository {
    boolean existsBySourceAndTarget(User source, User target);
    Optional<Follow> findBySourceAndTarget(User source, User target);
}
