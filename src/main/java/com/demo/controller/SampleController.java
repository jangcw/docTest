package com.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.PostListResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class SampleController {

    @Operation(summary = "헬로 API", description = "간단한 테스트용 API")
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping("/hello")
    public String sample(@Parameter(description = "조회할 게시글 ID",example = "1",required = true) @RequestParam("userid") String userid) {
    	
    	System.out.println("Hello Call!!");
        return "Hello Sample";
    }
     
    @Operation(summary = "게시글 조회", description = "게시글 조회 API")
    @ApiResponses({
        @ApiResponse(responseCode = "200",description = "조회 성공"),
        @ApiResponse(responseCode = "404",description = "해당 게시글을 찾을 수 없음")
    })
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostListResponseDto> findPost(@Parameter(description = "조회할 게시글 ID",example = "1",required = true)  @PathVariable("id") Long id){ 
        
    	PostListResponseDto rt = new PostListResponseDto();
    	rt.setTitle(""+id);
    	
        return ResponseEntity.ok().body(rt);
    }
    
}