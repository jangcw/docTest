package com.demo.config;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

    private final ApiCode apiCode;

    public BusinessException(ApiCode apiCode, String message) {
        super(message);
        this.apiCode = apiCode;
    }

    public ApiCode getApiCode() {
        return apiCode;
    }
}