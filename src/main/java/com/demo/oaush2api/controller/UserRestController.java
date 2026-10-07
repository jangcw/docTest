package com.demo.oaush2api.controller;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.config.ApiCode;
import com.demo.config.ApiMessage;
import com.demo.oaush2api.dto.User;
import com.demo.oaush2api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "사용자 관리", description = "사용자 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserRestController {

	private final UserService userService;
	
    @Operation(summary = "사용자 정보", description = "사용자정보 API")
    @ApiResponse(responseCode = "SUCCESS", description = "정상 응답")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiMessage<?>> userInfo(@Parameter(description = "사용자ID",example = "1",required = true) @PathVariable("userId") String userId) {
    	
    	Optional<User> User = userService.getUser(userId);
    	
    	if (!User.isEmpty()) {
    		return ResponseEntity.ok().body(User
      			  .map(ApiMessage::success)
      			  .get());
    		
    	}else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiMessage.fail(
                         ApiCode.NOT_FOUND,
                         "사용자를 찾을 수 없습니다."
                    ));
    	}
    	
    }
    
    @Operation(
    	    summary = "사용자 목록 조회",
    	    description = """
    	        사용자 검색 조건과 페이징 정보를 이용하여 사용자 목록을 조회합니다.

    	        검색 조건은 모두 선택사항입니다.
    	        입력하지 않은 검색 조건은 검색에서 제외됩니다.

    	        페이징:
    	        - page: 페이지 번호 (0부터 시작)
    	        - size: 한 페이지에 조회할 데이터 개수
    	        - sort: 정렬 기준
    	        """
    )
    @PostMapping("/searchUsers")
    public ResponseEntity<ApiMessage<?>> searchUsers(
    		@ParameterObject
    		@ModelAttribute User condition,
    		@ParameterObject
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "wcode",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        ApiMessage<?> result = userService.searchUsers(condition, pageable);

        return ResponseEntity.ok().body(result);
    }
    
    @Operation(summary = "사용자 저장", description = "사용자 정보를 저장합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "저장 성공"),
        @ApiResponse(responseCode = "500", description = "저장 실패")
    })
    @PutMapping("/save")
    public ResponseEntity<ApiMessage<?>> userSave(
    		@ParameterObject
    		@ModelAttribute User User) {
        
		userService.userSave(User);
        return ResponseEntity.ok(
                ApiMessage.success("사용자를 저장하였습니다.")
        );
    }

    @Operation(summary = "사용자 수정", description = "사용자 정보를 수정합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "저장 성공"),
        @ApiResponse(responseCode = "500", description = "저장 실패")
    })
    @PatchMapping("/update")
    public ResponseEntity<ApiMessage<?>> userUpdate(
    		@ParameterObject
    		@ModelAttribute User User) {
        
		userService.userUpdate(User);
        return ResponseEntity.ok(
                ApiMessage.success("사용자를 수정하였습니다.")
        );
    }
    
    @Operation(summary = "사용자 삭제", description = "사용자 정보를 삭제합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "삭제 성공"),
        @ApiResponse(responseCode = "500", description = "삭제 실패")
    })
    @DeleteMapping("/delete/{wcode}")
    public ResponseEntity<ApiMessage<?>> userDelete(@Parameter(description = "사용자ID",example = "1",required = true) @PathVariable("userId") String userId) {

    	userService.userDelete(userId);
        
    	return ResponseEntity.ok(
                ApiMessage.success("사용자를 삭제하였습니다.")
        );
    }

    @DeleteMapping("/userList")
    public ResponseEntity<ApiMessage<?>> userList() {

    	List<User> userList = userService.getUsers();
        
    	return ResponseEntity.ok(
                ApiMessage.success(userList)
        );
    }
    
    
}