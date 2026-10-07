package com.demo.oaush2api.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.config.ApiMessage;
import com.demo.oaush2api.dto.Role;
import com.demo.oaush2api.dto.User;
import com.demo.oaush2api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "사용자 관리", description = "사용자 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestController {

	private final UserService userService;
    
    @Operation(summary = "사용자 저장", description = "사용자 정보를 저장합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "저장 성공"),
        @ApiResponse(responseCode = "500", description = "저장 실패")
    })
    @PutMapping("/userSave")
    public ResponseEntity<ApiMessage<?>> userSave(
    		@ParameterObject
    		@ModelAttribute User user) {
        
    	user.setRole(Role.USER);
    	
		userService.userSave(user);
        return ResponseEntity.ok(
                ApiMessage.success("사용자를 저장하였습니다.")
        );
    }   
    
}