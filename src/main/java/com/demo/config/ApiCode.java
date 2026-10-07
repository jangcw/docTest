package com.demo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiCode {

    SUCCESS("SUCCESS", "정상적으로 처리되었습니다."),
    SYSTEM_ERROR("SYSTEM_ERROR", "서버 오류가 발생하였습니다."),
    UNAUTHORIZED_USER("UNAUTHORIZED_USER", "인증 정보가 없거나 유효하지 않은 토큰입니다."),
    ACCESS_DENIED("ACCESS_DENIED", "해당 요청에 대한 접근 권한이 없습니다."),
    NOT_FOUND("NOT_FOUND", "요청정보를 찾을 수 없습니다."),
    INVALID_PARAMETER("INVALID_PARAMETER", "잘못된 요청입니다."),
    DUPLICATE_ERROR("DUPLICATE_DATA", "이미 존재합니다."),
    INTERNAL_ERROR("INTERNAL_ERROR", "서버 오류가 발생했습니다."),
	SAVE_ERROR("SAVE_ERROR", "저장에 실패하였습니다."),
	DELETE_ERROR("DELETE_ERROR", "삭제에 실패하였습니다.");

    private final String code;
    private final String message;
}