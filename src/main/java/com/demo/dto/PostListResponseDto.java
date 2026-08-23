package com.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostListResponseDto {

    @Schema(description = "게시글 제목",example = "제목")
    private String title;

    @Schema(description = "게시글 내용",example = "내용")
    private String content;

    @Schema(description = "게시글 작성자",example = "이름")
    private String name;
}