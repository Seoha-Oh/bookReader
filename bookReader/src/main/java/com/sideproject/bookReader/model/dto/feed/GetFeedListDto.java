package com.sideproject.bookReader.model.dto.feed;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFeedListDto {

    List<GetFeedDto> getFeedDtoList;
    private long totalElements; // 전체 데이터 개수
    private int totalPages;     // 전체 페이지 수
    private int currentPage;    // 현재 페이지 번호

}
