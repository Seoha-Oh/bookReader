package com.sideproject.bookReader.repository.custom;

import com.sideproject.bookReader.model.Feed;
import com.sideproject.bookReader.model.dto.feed.GetFeedDto;
import com.sideproject.bookReader.model.dto.feed.GetFeedListDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FeedCustomRepository {

    GetFeedDto findFeedDtoFetchJoin(Long feedId);

    List<GetFeedDto> findFeedDtosFetchJoin();

    Page<GetFeedDto> findFeedsByFollowing(Long userId, Pageable pageable);

    Optional<Feed> findFetchJoin(Long feedId);

}
