package com.sideproject.bookReader.repository;

import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.repository.custom.FeedCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedCustomRepository {

}
