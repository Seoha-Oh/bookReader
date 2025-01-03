package com.sideproject.bookReader.controller;


import com.sideproject.bookReader.model.dto.feed.CreateFeedDto;
import com.sideproject.bookReader.model.dto.feed.GetFeedDto;
import com.sideproject.bookReader.model.dto.feed.GetFeedListDto;
import com.sideproject.bookReader.model.dto.feed.UpdateFeedDto;
import com.sideproject.bookReader.service.facade.FeedFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feeds")
@AllArgsConstructor
public class FeedController {

    private final FeedFacade feedFacade;

    @PostMapping()
    public void writeFeed(@RequestBody CreateFeedDto createFeedDto) {
        feedFacade.writeFeed(createFeedDto);
    }

    @GetMapping("/{feedId}")
    public GetFeedDto getFeedDetail(@PathVariable Long feedId) {
        return feedFacade.getFeedDetail(feedId);
    }

    @GetMapping("/following")
    public GetFeedListDto getFeedsByFollowing(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);

        return feedFacade.getFeedList(pageable);
    }

    @PutMapping("/{feedId}")
    public void updateFeed(@PathVariable Long feedId,
        @RequestBody UpdateFeedDto updateFeedDto) {
        feedFacade.updateFeed(feedId, updateFeedDto);
    }

    @DeleteMapping("/{feedId}")
    public void deleteFeed(@PathVariable Long feedId) {
        feedFacade.removeFeed(feedId);
    }

}
