package com.demo.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {

    // 현재 페이지
    private Integer page;

    // 페이지당 크기
    private Integer size;

    // 현재 페이지에 있는 요소 개수
    private Integer currentElement;

    // 총 페이지 수
    private Integer totalPage;

    // 전체 데이터 개수
    private Long totalElement;
}
